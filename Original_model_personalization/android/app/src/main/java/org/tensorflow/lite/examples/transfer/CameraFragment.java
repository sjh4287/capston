/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.tensorflow.lite.examples.transfer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraX.LensFacing;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.ImageReaderMode;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.ImageProxy.PlaneProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState;
import org.tensorflow.lite.examples.transfer.TransferLearningModel.Prediction;
import org.tensorflow.lite.examples.transfer.databinding.CameraFragmentBinding;

/**
 * The main fragment of the classifier.
 *
 * Camera functionality (through CameraX) is heavily based on the official example:
 * https://github.com/android/camera/tree/master/CameraXBasic.
 */
public class CameraFragment extends Fragment {

  private static final int LOWER_BYTE_MASK = 0xFF;

  private static final String TAG = CameraFragment.class.getSimpleName();

  private static final LensFacing LENS_FACING = LensFacing.BACK;

  private static final int LONG_PRESS_DURATION = 500;
  private static final int SAMPLE_COLLECTION_DELAY = 300;

  private TextureView viewFinder;

  private Integer viewFinderRotation = null;

  private Size bufferDimens = new Size(0, 0);
  private Size viewFinderDimens = new Size(0, 0);

  private CameraFragmentViewModel viewModel;
  private TransferLearningModelWrapper tlModel;

  private long sampleCollectionButtonPressedTime;
  private boolean isCollectingSamples = false;
  private final Handler sampleCollectionHandler = new Handler(Looper.getMainLooper());  //main UI thread

  private final HelpDialog helpDialog = new HelpDialog();

  // When the user presses the "add sample" button for some class,
  // that class will be added to this queue. It is later extracted by
  // InferenceThread and processed.
  private final ConcurrentLinkedQueue<String> addSampleRequests = new ConcurrentLinkedQueue<>();

  private final LoggingBenchmark inferenceBenchmark = new LoggingBenchmark("InferenceBench");

  /**
   * Set up a responsive preview for the view finder.
   */
  private void startCamera() {  //Start Camera
    viewFinderRotation = getDisplaySurfaceRotation(viewFinder.getDisplay());  //화면회전 0, 90, 180, 270
    if (viewFinderRotation == null) { //null 이면
      viewFinderRotation = 0;         //0으로 설정
    }

    DisplayMetrics metrics = new DisplayMetrics();  //디스플레이에 대한 자료를 가지고 있는 구조, 크기, 폰트 크기 등
    viewFinder.getDisplay().getRealMetrics(metrics);  //Display 에서 현재 사용 가능한 가장 큰 크기 반환
    Rational screenAspectRatio = new Rational(metrics.widthPixels, metrics.heightPixels); //불변형 데이터타입, Int 쌍 포함, 디스플레이의 가로 세로 저장

    PreviewConfig config = new PreviewConfig.Builder()  //카메라가 사용하는 미리보기 뷰 빌더
        .setLensFacing(LENS_FACING) //렌즈 방향 - 후면 카메라
        .setTargetAspectRatio(screenAspectRatio)  //가로 세로 비율 결정
        .setTargetRotation(viewFinder.getDisplay().getRotation()) //회전 결정
        .build(); //build

    Preview preview = new Preview(config);  //미리보기 뷰 생성

    preview.setOnPreviewOutputUpdateListener(previewOutput -> { //한번만 동작한다 ??? 왜지?

      ViewGroup parent = (ViewGroup) viewFinder.getParent();  //ViewGroup 설정
      parent.removeView(viewFinder);  //texture view 삭제
      parent.addView(viewFinder, 0);  //texture view 다시 추가

      viewFinder.setSurfaceTexture(previewOutput.getSurfaceTexture());  //뷰 설정????

      Integer rotation = getDisplaySurfaceRotation(viewFinder.getDisplay());  //화면회전
      updateTransform(rotation, previewOutput.getTextureSize(), viewFinderDimens);  //화면 형태 맞추기
    });

    viewFinder.addOnLayoutChangeListener((
        view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
      Size newViewFinderDimens = new Size(right - left, bottom - top);
      Integer rotation = getDisplaySurfaceRotation(viewFinder.getDisplay());
      updateTransform(rotation, bufferDimens, newViewFinderDimens);
    });

    HandlerThread inferenceThread = new HandlerThread("InferenceThread"); //추론 thread
    inferenceThread.start();  //thread 시작
    ImageAnalysisConfig analysisConfig = new ImageAnalysisConfig.Builder()  //imageAnalysisConfig builder
        .setLensFacing(LENS_FACING) //렌즈 방향 - 후면카메라
        .setCallbackHandler(new Handler(inferenceThread.getLooper())) // handler 설정
        .setImageReaderMode(ImageReaderMode.ACQUIRE_LATEST_IMAGE) //이미지 모드 설정, 최신 이미지 획득, 기존 이미지 버리기
        .setTargetRotation(viewFinder.getDisplay().getRotation()) //회전 설정
        .build();

    ImageAnalysis imageAnalysis = new ImageAnalysis(analysisConfig);  //ImageAnalysis 는 CPU 에서 분석할 수 있는 이미지 제공
    imageAnalysis.setAnalyzer(inferenceAnalyzer); //이미지를 분석하는 분석기 설정, 여기서는 추론 분석기

    CameraX.bindToLifecycle(this, preview, imageAnalysis);  //lifecycle 에 따라 카메라가 열림, 시작, 중지 및 닫힘이 결정
  }

  private final ImageAnalysis.Analyzer inferenceAnalyzer =  //이미지 분석을 위한 인터페이스
      (imageProxy, rotationDegrees) -> {
        final String imageId = UUID.randomUUID().toString();  //랜덤 ID 생성

        inferenceBenchmark.startStage(imageId, "preprocess"); //실행 시간 측정을 위한 클래스, startStage
        float[][][] rgbImage =  //RGB 이미지 처리
            prepareCameraImage(yuvCameraImageToBitmap(imageProxy), rotationDegrees);
        inferenceBenchmark.endStage(imageId, "preprocess"); //실행 시간 측정 종료

        // Adding samples is also handled by inference thread / use case.
        // We don't use CameraX ImageCapture since it has very high latency (~650ms on Pixel 2 XL)
        // even when using .MIN_LATENCY.
        String sampleClass = addSampleRequests.poll();  //샘플 queue 에서 하나 꺼냄
        if (sampleClass != null) {  //queue 에서 꺼낸 값이 null 이 아닐 때
          inferenceBenchmark.startStage(imageId, "addSample");  //실행 시간 측정
          try {
            tlModel.addSample(rgbImage, sampleClass).get(); //model 에 이미지, 라벨 값을 입력하여 샘플을 추가하고 return null
          } catch (ExecutionException e) {
            throw new RuntimeException("Failed to add sample to model", e.getCause());
          } catch (InterruptedException e) {
            // no-op
          }

          viewModel.increaseNumSamples(sampleClass);  //  클래스 이름과 함께 샘플의 수 +1
          inferenceBenchmark.endStage(imageId, "addSample");  //시간측정 종료

        } else { //queue 에서 꺼낸 값이 없을 때
          // We don't perform inference when adding samples, since we should be in capture mode
          // at the time, so the inference results are not actually displayed.
          inferenceBenchmark.startStage(imageId, "predict");
          Prediction[] predictions = tlModel.predict(rgbImage);
          if (predictions == null) {
            return;
          }
          inferenceBenchmark.endStage(imageId, "predict");

          for (Prediction prediction : predictions) {
            viewModel.setConfidence(prediction.getClassName(), prediction.getConfidence());
          }
        }

        inferenceBenchmark.finish(imageId);
      };

  public final View.OnTouchListener onAddSampleTouchListener =
      (view, motionEvent) -> {
        switch (motionEvent.getAction()) {
          case MotionEvent.ACTION_DOWN: //터치시
            isCollectingSamples = true; //isCollectingSample true 로 변경
            sampleCollectionButtonPressedTime = SystemClock.uptimeMillis(); //버튼 클릭 순간의 시간
            sampleCollectionHandler.post( //thread 실행
                new Runnable() {
                  @Override
                  public void run() {
                    long timePressed =  //버튼 눌러진 시간
                        SystemClock.uptimeMillis() - sampleCollectionButtonPressedTime; //현재시간 - 버튼 클릭 순간의 시간
                    view.findViewById(view.getId()).performClick(); //클릭한 view 의 OnClickListener 가 있으면 실행
                    if (timePressed < LONG_PRESS_DURATION) {        //버튼이 눌러진 시간 < 0.5초
                      sampleCollectionHandler.postDelayed(this, LONG_PRESS_DURATION); //다시 0.5초 뒤에 다시 쓰레드 실행
                    } else if (isCollectingSamples) { //isCollectingSample = true, 버튼 눌러진시간 >= 0.5초
                      String className = getClassNameFromResourceId(view.getId());  //클래스 이름 = view 의 ID
                      viewModel.setNumCollectedSamples(
                          viewModel.getNumSamples().getValue().get(className) + 1); //ViewModel 의 샘플 수 ++
                      sampleCollectionHandler.postDelayed(this, SAMPLE_COLLECTION_DELAY); //0.3초 뒤에 다시 실행
                      viewModel.setSampleCollectionLongPressed(true); //ViewModel 의 LongPressed true 로 설정
                    }
                  }
                });
            break;
          case MotionEvent.ACTION_UP: //터치 해제시
            sampleCollectionHandler.removeCallbacksAndMessages(null); //쓰레드의 진행중인 내용 중단
            isCollectingSamples = false;  //isCollectingSample false 로
            viewModel.setSampleCollectionLongPressed(false);  //ViewModel 의 LongPressed false 로 설정
            break;
          default:
            break;
        }
        return true;
      };

  public final View.OnClickListener onAddSampleClickListener =
      view -> {
        String className = getClassNameFromResourceId(view.getId());  //클래스 이름 get, 1, 2, 3, 4 중에 하나 버튼별로
        addSampleRequests.add(className); //클래스 이름으로 Queue 에 추가
      };

  private String getClassNameFromResourceId(int id) {
    String className;
    if (id == R.id.class_btn_1) {
      className = "1";
    } else if (id == R.id.class_btn_2) {
      className = "2";
    } else if (id == R.id.class_btn_3) {
      className = "3";
    } else if (id == R.id.class_btn_4) {
      className = "4";
    } else {
      throw new RuntimeException("Listener called for unexpected view");
    }
    return className;
  }

  /**
   * Fit the camera preview into [viewFinder].
   *
   * @param rotation view finder rotation.
   * @param newBufferDimens camera preview dimensions.
   * @param newViewFinderDimens view finder dimensions.
   */
  private void updateTransform(Integer rotation, Size newBufferDimens, Size newViewFinderDimens) {  //회전, preview 사이즈, view finder 사이즈
    if (Objects.equals(rotation, viewFinderRotation)  //입력값이 기존과 동일하면 return
        && Objects.equals(newBufferDimens, bufferDimens)
        && Objects.equals(newViewFinderDimens, viewFinderDimens)) {
      return;
    }

    if (rotation == null) { //회전이 null 이면 return
      return;
    } else {  //회전 설정
      viewFinderRotation = rotation;
    }

    if (newBufferDimens.getWidth() == 0 || newBufferDimens.getHeight() == 0) {  //preview 사이즈 0 이면 return
      return;
    } else {
      bufferDimens = newBufferDimens; //preview size 설정
    }

    if (newViewFinderDimens.getWidth() == 0 || newViewFinderDimens.getHeight() == 0) {  //view finder 사이즈 0이면 return
      return;
    } else {
      viewFinderDimens = newViewFinderDimens; //view finder 사이즈 설정
    }

    Log.d(TAG, String.format("Applying output transformation.\n"
        + "View finder size: %s.\n"
        + "Preview output size: %s\n"
        + "View finder rotation: %s\n", viewFinderDimens, bufferDimens, viewFinderRotation));
    Matrix matrix = new Matrix();  //행렬?

    float centerX = viewFinderDimens.getWidth() / 2f; //x 축 center = 가로 / 2
    float centerY = viewFinderDimens.getHeight() / 2f;//y 축 center = 세로 / 2

    matrix.postRotate(-viewFinderRotation.floatValue(), centerX, centerY);  //회전, x, y, 회전 후에 행렬 대응?

    float bufferRatio = bufferDimens.getHeight() / (float) bufferDimens.getWidth(); //preview 세로 / 가로

    int scaledWidth;
    int scaledHeight;
    if (viewFinderDimens.getWidth() > viewFinderDimens.getHeight()) { //if viewfinder 가로 > 세로
      scaledHeight = viewFinderDimens.getWidth();         // 더 긴쪽을 세로로 설정
      scaledWidth = Math.round(viewFinderDimens.getWidth() * bufferRatio);
    } else {
      scaledHeight = viewFinderDimens.getHeight();        // 세로 설정
      scaledWidth = Math.round(viewFinderDimens.getHeight() * bufferRatio);
    }

    float xScale = scaledWidth / (float) viewFinderDimens.getWidth(); //가로 / viewfinder 가로
    float yScale = scaledHeight / (float) viewFinderDimens.getHeight(); //세로 / viewfinder 세로
    matrix.preScale(xScale, yScale, centerX, centerY);//0.913, 1.0, 540, 1096,
    //preScale(sx, sy, px, py) -> M' = M * S(sx, sy, px, py)

    viewFinder.setTransform(matrix);  //행렬에 맞춰서 textureView 설정
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    tlModel = new TransferLearningModelWrapper(getActivity());  //모델 wrapper 생성
    viewModel = ViewModelProviders.of(this).get(CameraFragmentViewModel.class); //viewModel 생성
    viewModel.setTrainBatchSize(tlModel.getTrainBatchSize()); //훈련 배치 사이즈 설정
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
    CameraFragmentBinding dataBinding =  //프래그먼트에 데이터 바인딩 - MVC 패턴을 손쉽게 사용
        DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false);
    dataBinding.setLifecycleOwner(getViewLifecycleOwner());
    dataBinding.setVm(viewModel);
    View rootView = dataBinding.getRoot();

    for (int i = 0; i < 1000; i++) {
      addSampleRequests.add("1");
      addSampleRequests.add("2");
      addSampleRequests.add("3");
      addSampleRequests.add("4");
    }

    for (int buttonId : new int[] { //버튼 클릭리스너
        R.id.class_btn_1, R.id.class_btn_2, R.id.class_btn_3, R.id.class_btn_4}) {
      rootView.findViewById(buttonId).setOnClickListener(onAddSampleClickListener); //클릭시 queue 에 클래스 이름 추가
      rootView.findViewById(buttonId).setOnTouchListener(onAddSampleTouchListener); //길게 클릭시 Listener
    }

    if (viewModel.getCaptureMode().getValue()) {  //CaptureMode true 면
      ((RadioButton) rootView.findViewById(R.id.capture_mode_button)).setChecked(true); //training 버튼 체크
    } else {                                      //false 면
      ((RadioButton) rootView.findViewById(R.id.inference_mode_button)).setChecked(true); //inference 버튼 체크
    }

    RadioGroup toggleButtonGroup = rootView.findViewById(R.id.mode_toggle_button_group);  //training, inference 버튼
    toggleButtonGroup.setOnCheckedChangeListener( //changed Listener
        (radioGroup, checkedId) -> {
          if (viewModel.getTrainingState().getValue() == TrainingState.NOT_STARTED) { //TrainingState == Not_Started 이면
            ((RadioButton) rootView.findViewById(R.id.capture_mode_button)).setChecked(true); //training 버튼 체크, 추론 버튼 해제
            ((RadioButton) rootView.findViewById(R.id.inference_mode_button)).setChecked(false);

            Snackbar.make(  //스낵바,
                    requireActivity().findViewById(R.id.classes_bar),
                    "Inference can only start after training is done.",
                    BaseTransientBottomBar.LENGTH_LONG)
                .show();
          } else { //Not_Started 아니고,
            if (checkedId == R.id.capture_mode_button) {  //training 버튼 누르면
              viewModel.setCaptureMode(true);             //captureMode true 로
            } else {                                      //inference 버튼 누르면
              viewModel.setCaptureMode(false);            //captureMode false 로
              Snackbar.make(  //스낵바
                      requireActivity().findViewById(R.id.classes_bar),
                      "Point your camera at one of the trained objects.",
                      BaseTransientBottomBar.LENGTH_LONG)
                  .show();
            }
          }
        });

    Button helpButton = rootView.findViewById(R.id.help_button);  //help 버튼, 다이얼로그를 보여줌
    helpButton.setOnClickListener(
        (button) -> {
          helpDialog.show(requireActivity().getSupportFragmentManager(), "Help Dialog");
        });
    // Display HelpDialog when opened.
    helpDialog.show(requireActivity().getSupportFragmentManager(), "Help Dialog");

    return dataBinding.getRoot(); //데이터바운딩 리턴?
  }

  @Override
  public void onViewCreated(View view, Bundle bundle) {
    super.onViewCreated(view, bundle);

    viewFinder = requireActivity().findViewById(R.id.view_finder);
    viewFinder.post(this::startCamera); //viewFinder 가 호스팅이 끝난 후에 startCamera 호출

    viewModel
        .getTrainingState()
        .observe(
            getViewLifecycleOwner(),
            trainingState -> {
              switch (trainingState) {
                case STARTED:
                  tlModel.enableTraining((epoch, loss) -> viewModel.setLastLoss(loss));
                  if (!viewModel.getInferenceSnackbarWasDisplayed().getValue()) {
                    Snackbar.make(
                            requireActivity().findViewById(R.id.classes_bar),
                            R.string.switch_to_inference_hint,
                            BaseTransientBottomBar.LENGTH_LONG)
                        .show();
                    viewModel.markInferenceSnackbarWasCalled();
                  }
                  break;
                case PAUSED:
                  tlModel.disableTraining();
                  break;
                case NOT_STARTED:
                  break;
              }
            });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    tlModel.close();
    tlModel = null;
  }

  private static Integer getDisplaySurfaceRotation(Display display) {
    if (display == null) {
      return null;
    }

    switch (display.getRotation()) {  //Display.getRotation
      case Surface.ROTATION_0: return 0;
      case Surface.ROTATION_90: return 90;
      case Surface.ROTATION_180: return 180;
      case Surface.ROTATION_270: return 270;
      default: return null;
    }
  }

  private static Bitmap yuvCameraImageToBitmap(ImageProxy imageProxy) {
    if (imageProxy.getFormat() != ImageFormat.YUV_420_888) {  //이미지의 포멧이 맞지 않으면 throw
      throw new IllegalArgumentException(   //YUV_420_888 - 다중 평면구조, Y: 밝기, U, V: 색상 신호
          "Expected a YUV420 image, but got " + imageProxy.getFormat());
    }

    PlaneProxy yPlane = imageProxy.getPlanes()[0];  //x평면 = Y
    PlaneProxy uPlane = imageProxy.getPlanes()[1];  //y평면 = U
                                                  //getPlanes()[2] = V
    int width = imageProxy.getWidth();  //640
    int height = imageProxy.getHeight();  //480
    byte[][] yuvBytes = new byte[3][]; //yuv 저장할 byte 배열
    int[] argbArray = new int[width * height];  //307200
    for (int i = 0; i < imageProxy.getPlanes().length; i++) { //루프 3
      final ByteBuffer buffer = imageProxy.getPlanes()[i].getBuffer();  //픽셀 버퍼 반환
      yuvBytes[i] = new byte[buffer.capacity()]; //y, u, v 값 각각 yuvBytes[0], yuvBytes[1], yuvBytes[2] 에 저장
      buffer.get(yuvBytes[i]);
    }

    ImageUtils.convertYUV420ToARGB8888( //yuv 를 argb 로 변환해서 argbArray 에 저장
        yuvBytes[0],
        yuvBytes[1],
        yuvBytes[2],
        width,
        height,
        yPlane.getRowStride(),
        uPlane.getRowStride(),
        uPlane.getPixelStride(),
        argbArray);

    return Bitmap.createBitmap(argbArray, width, height, Config.ARGB_8888); //argb 로 변경한 bitmap 생성해서 return
  }

  /**
   * Normalizes a camera image to [0; 1], cropping it to size expected by the model and adjusting
   * for camera rotation.
   */
  private static float[][][] prepareCameraImage(Bitmap bitmap, int rotationDegrees) { //이미지 준비
    int modelImageSize = TransferLearningModelWrapper.IMAGE_SIZE; //이미지 크기 받아오기 = 224

    Bitmap paddedBitmap = padToSquare(bitmap);  //bitmap 을 padding 을 채운 정사각형 이미지로 변환
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(  //이미지의 비율을 변경한다.
        paddedBitmap, modelImageSize, modelImageSize, true);

    Matrix rotationMatrix = new Matrix();
    rotationMatrix.postRotate(rotationDegrees); //행렬을 회전시킨다
    Bitmap rotatedBitmap = Bitmap.createBitmap( //회전된 비트맵 이미지 생성
        scaledBitmap, 0, 0, modelImageSize, modelImageSize, rotationMatrix, false);

    float[][][] normalizedRgb = new float[modelImageSize][modelImageSize][3]; //float[224][224][3]
    for (int y = 0; y < modelImageSize; y++) {  //224 * 224 루프
      for (int x = 0; x < modelImageSize; x++) {
        int rgb = rotatedBitmap.getPixel(x, y);   // 비트맵 이미지 픽셀의 Color 값 리턴받음
        //color 값은 alpha & 0xff << 24 | red & 0xff << 16 | green & 0xff << 8 | blue & 0xff
        float r = ((rgb >> 16) & LOWER_BYTE_MASK) * (1 / 255.f);  //red
        float g = ((rgb >> 8) & LOWER_BYTE_MASK) * (1 / 255.f);   //green
        float b = (rgb & LOWER_BYTE_MASK) * (1 / 255.f);          //blue

        normalizedRgb[y][x][0] = r;
        normalizedRgb[y][x][1] = g;
        normalizedRgb[y][x][2] = b;
      }
    }
    //224 * 224 이미지의 r g b 값 return
    return normalizedRgb;
  }

  private static Bitmap padToSquare(Bitmap source) {
    int width = source.getWidth();  //가로
    int height = source.getHeight();  //세로

    int paddingX = width < height ? (height - width) / 2 : 0; //패딩 x = 세로가 더 길면, 세로 - 가로 / 2, 아니면 0
    int paddingY = height < width ? (width - height) / 2 : 0; //패딩 y = 가로가 더 길면, 가로 = 세로 / 2, 아니면 0
    Bitmap paddedBitmap = Bitmap.createBitmap(  //Bitmap 생성, 가로 + 패딩 x * 2 = 가로, 세로 + 패딩 y * 2 = 세로,
        width + 2 * paddingX, height + 2 * paddingY, Config.ARGB_8888); //Config: 사용 가능한 비트맵 구성, 품질, 색상 등을 결정
                                                    //ARGB_8888 = 각 픽셀이 4바이트로 저장, Alpha, R, G, B 저장
    Canvas canvas = new Canvas(paddedBitmap); //Canvas: Draw 관련 call 담당, paddedBitmap 을 사용하여 구성 - paddedBitmap 에 그리기
    canvas.drawARGB(0xFF, 0xFF, 0xFF, 0xFF);   //캔버스를 지정된 색으로 채움 - 흰색
    canvas.drawBitmap(source, paddingX, paddingY, null);  //source 를 통해 padding x, y 만큼 떨어진곳에서부터 draw
    return paddedBitmap;  //bitmap 리턴
  }

  // Binding adapters:

  @BindingAdapter({"captureMode", "inferenceText", "captureText"})
  public static void setClassSubtitleText(
      TextView view, boolean captureMode, Float inferenceText, Integer captureText) {
    if (captureMode) {
      view.setText(captureText != null ? Integer.toString(captureText) : "0");
    } else {
      view.setText(
          String.format(Locale.getDefault(), "%.2f", inferenceText != null ? inferenceText : 0.f));
    }
  }

  @BindingAdapter({"android:visibility"})
  public static void setViewVisibility(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @BindingAdapter({"highlight"})
  public static void setClassButtonHighlight(View view, boolean highlight) {
    int drawableId;
    if (highlight) {
      drawableId = R.drawable.btn_default_highlight;
    } else {
      drawableId = R.drawable.btn_default;
    }
    view.setBackground(AppCompatResources.getDrawable(view.getContext(), drawableId));
  }
}
