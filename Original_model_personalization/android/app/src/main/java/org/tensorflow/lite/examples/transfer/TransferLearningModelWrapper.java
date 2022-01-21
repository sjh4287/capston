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

import android.content.Context;
import android.os.Build;
import android.os.ConditionVariable;

import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.tensorflow.lite.examples.transfer.TransferLearningModel.LossConsumer;
import org.tensorflow.lite.examples.transfer.TransferLearningModel.Prediction;

/**
 * App-layer wrapper for {@link TransferLearningModel}.
 *
 * <p>This wrapper allows to run training continuously, using start/stop API, in contrast to
 * run-once API of {@link TransferLearningModel}.
 */
public class TransferLearningModelWrapper implements Closeable {
  public static final int IMAGE_SIZE = 224;

  private final TransferLearningModel model;

  private final ConditionVariable shouldTrain = new ConditionVariable();
  private volatile LossConsumer lossConsumer;

  @RequiresApi(api = Build.VERSION_CODES.O)
  TransferLearningModelWrapper(Context context) {
    model = //모델 생성(모델 로더, 클래스들)
        new TransferLearningModel(
            new ModelLoader(context, "model"), Arrays.asList("1", "2", "3", "4"));

    new Thread(() -> {
      while (!Thread.interrupted()) { //쓰레드가 interrupted 되지 않으면 무한루프
        shouldTrain.block();  //ConditionVariable, block 상태이면 open 상태가 될 때 까지 동작 중지
        try {
          model.train(1, lossConsumer).get(); //모델 학습, 1 epoch, lossConsumer = ViewModel.setLastLoss(loss)
        } catch (ExecutionException e) {
          throw new RuntimeException("Exception occurred during model training", e.getCause());
        } catch (InterruptedException e) {
          // no-op
        }
      }
    }).start(); //쓰레드 시작
  }

  // This method is thread-safe.
  public Future<Void> addSample(float[][][] image, String className) {
    return model.addSample(image, className);
  }

  // This method is thread-safe, but blocking.
  public Prediction[] predict(float[][][] image) {
    return model.predict(image);
  }

  public int getTrainBatchSize() {
    return model.getTrainBatchSize();
  }

  /**
   * Start training the model continuously until {@link #disableTraining() disableTraining} is
   * called.
   *
   * @param lossConsumer callback that the loss values will be passed to.
   */
  public void enableTraining(LossConsumer lossConsumer) {
    this.lossConsumer = lossConsumer; //lossConsumer 설정, ViewModel.setLastLoss(loss)
    shouldTrain.open(); //ConditionVariable open 상태로 변경 -> 학습 시작
  }

  /**
   * Stops training the model.
   */
  public void disableTraining() {
    shouldTrain.close(); //ConditionVariable close 상태로 변경 -> 학습 중단
  }

  /** Frees all model resources and shuts down all background threads. */
  public void close() {
    model.close();
  }
}
