package org.tensorflow.lite.examples.transfer.databinding;
import org.tensorflow.lite.examples.transfer.R;
import org.tensorflow.lite.examples.transfer.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CameraFragmentBindingImpl extends CameraFragmentBinding implements org.tensorflow.lite.examples.transfer.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.view_finder, 17);
        sViewsWithIds.put(R.id.help_button, 18);
        sViewsWithIds.put(R.id.mode_toggle_button_group, 19);
        sViewsWithIds.put(R.id.capture_mode_button, 20);
        sViewsWithIds.put(R.id.inference_mode_button, 21);
        sViewsWithIds.put(R.id.classes_bar, 22);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    @NonNull
    private final android.widget.TextView mboundView10;
    @NonNull
    private final android.widget.TextView mboundView12;
    @NonNull
    private final android.widget.TextView mboundView14;
    @NonNull
    private final android.widget.TextView mboundView16;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.LinearLayout mboundView3;
    @NonNull
    private final android.widget.LinearLayout mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.LinearLayout mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CameraFragmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private CameraFragmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9
            , (android.widget.RadioButton) bindings[20]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.Button) bindings[18]
            , (android.widget.RadioButton) bindings[21]
            , (android.widget.RadioGroup) bindings[19]
            , (android.widget.LinearLayout) bindings[4]
            , (android.view.TextureView) bindings[17]
            );
        this.classBtn1.setTag(null);
        this.classBtn2.setTag(null);
        this.classBtn3.setTag(null);
        this.classBtn4.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView10 = (android.widget.TextView) bindings[10];
        this.mboundView10.setTag("subtitle");
        this.mboundView12 = (android.widget.TextView) bindings[12];
        this.mboundView12.setTag("subtitle");
        this.mboundView14 = (android.widget.TextView) bindings[14];
        this.mboundView14.setTag("subtitle");
        this.mboundView16 = (android.widget.TextView) bindings[16];
        this.mboundView16.setTag("subtitle");
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.LinearLayout) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView5 = (android.widget.LinearLayout) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.LinearLayout) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.trainStartButton.setTag(null);
        setRootTag(root);
        // listeners
        mCallback2 = new org.tensorflow.lite.examples.transfer.generated.callback.OnClickListener(this, 2);
        mCallback1 = new org.tensorflow.lite.examples.transfer.generated.callback.OnClickListener(this, 1);
        mCallback3 = new org.tensorflow.lite.examples.transfer.generated.callback.OnClickListener(this, 3);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x400L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.vm == variableId) {
            setVm((org.tensorflow.lite.examples.transfer.CameraFragmentViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVm(@Nullable org.tensorflow.lite.examples.transfer.CameraFragmentViewModel Vm) {
        this.mVm = Vm;
        synchronized(this) {
            mDirtyFlags |= 0x200L;
        }
        notifyPropertyChanged(BR.vm);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeVmNumCollectedSamples((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 1 :
                return onChangeVmNeededSamples((androidx.lifecycle.LiveData<java.lang.Integer>) object, fieldId);
            case 2 :
                return onChangeVmConfidence((androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Float>>) object, fieldId);
            case 3 :
                return onChangeVmFirstChoice((androidx.lifecycle.LiveData<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeVmCaptureMode((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
            case 5 :
                return onChangeVmTrainingState((androidx.lifecycle.LiveData<org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState>) object, fieldId);
            case 6 :
                return onChangeVmNumSamples((androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Integer>>) object, fieldId);
            case 7 :
                return onChangeVmLastLoss((androidx.lifecycle.LiveData<java.lang.Float>) object, fieldId);
            case 8 :
                return onChangeVmGetSampleCollectionLongPressed((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeVmNumCollectedSamples(androidx.lifecycle.MutableLiveData<java.lang.Integer> VmNumCollectedSamples, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmNeededSamples(androidx.lifecycle.LiveData<java.lang.Integer> VmNeededSamples, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmConfidence(androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Float>> VmConfidence, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFirstChoice(androidx.lifecycle.LiveData<java.lang.String> VmFirstChoice, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmCaptureMode(androidx.lifecycle.MutableLiveData<java.lang.Boolean> VmCaptureMode, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmTrainingState(androidx.lifecycle.LiveData<org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState> VmTrainingState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmNumSamples(androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Integer>> VmNumSamples, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmLastLoss(androidx.lifecycle.LiveData<java.lang.Float> VmLastLoss, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmGetSampleCollectionLongPressed(androidx.lifecycle.MutableLiveData<java.lang.Boolean> VmGetSampleCollectionLongPressed, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean vmFirstChoiceJavaLangString4 = false;
        boolean vmCaptureModeVmFirstChoiceJavaLangString1BooleanFalse = false;
        int androidxDatabindingViewDataBindingSafeUnboxVmNeededSamplesGetValue = 0;
        boolean vmNeededSamplesInt0 = false;
        boolean vmFirstChoiceJavaLangString3 = false;
        boolean vmTrainingStateTrainingStatePAUSED = false;
        org.tensorflow.lite.examples.transfer.CameraFragmentViewModel vm = mVm;
        java.lang.Boolean vmGetSampleCollectionLongPressedGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> vmNumCollectedSamples = null;
        boolean vmCaptureMode = false;
        java.lang.Boolean vmCaptureModeGetValue = null;
        java.util.Map<java.lang.String,java.lang.Integer> vmNumSamplesGetValue = null;
        java.lang.Float vmConfidence4 = null;
        boolean vmCaptureModeVmFirstChoiceJavaLangString3BooleanFalse = false;
        androidx.lifecycle.LiveData<java.lang.Integer> vmNeededSamples = null;
        boolean vmCaptureModeVmNeededSamplesInt0BooleanFalse = false;
        java.lang.Integer vmNeededSamplesGetValue = null;
        androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Float>> vmConfidence = null;
        androidx.lifecycle.LiveData<java.lang.String> vmFirstChoice = null;
        boolean VmNeededSamplesInt01 = false;
        boolean vmTrainingStateTrainingStateSTARTED = false;
        java.lang.Float vmConfidence2 = null;
        java.lang.Integer vmNumSamples3 = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue = false;
        java.lang.Float vmConfidence3 = null;
        boolean vmLastLossJavaLangObjectNull = false;
        java.lang.String stringFormatJavaLangStringLoss3fVmLastLoss = null;
        boolean vmNeededSamplesInt0VmTrainingStateTrainingStateNOTSTARTEDBooleanFalse = false;
        boolean androidxDatabindingViewDataBindingSafeUnboxVmGetSampleCollectionLongPressedGetValue = false;
        java.lang.String vmFirstChoiceGetValue = null;
        boolean vmCaptureModeVmFirstChoiceJavaLangString2BooleanFalse = false;
        java.lang.Integer vmNumSamples4 = null;
        boolean vmTrainingStateTrainingStateNOTSTARTED = false;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> VmCaptureMode1 = null;
        java.lang.Integer vmNumSamples1 = null;
        java.lang.Integer vmNumCollectedSamplesGetValue = null;
        boolean vmFirstChoiceJavaLangString1 = false;
        java.lang.String stringFormatJavaLangStringDSamplesCollectedVmNumCollectedSamples = null;
        org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState vmTrainingStateGetValue = null;
        java.lang.Float vmLastLossGetValue = null;
        androidx.lifecycle.LiveData<org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState> vmTrainingState = null;
        androidx.lifecycle.LiveData<java.util.Map<java.lang.String,java.lang.Integer>> vmNumSamples = null;
        boolean vmCaptureModeVmFirstChoiceJavaLangString4BooleanFalse = false;
        androidx.lifecycle.LiveData<java.lang.Float> vmLastLoss = null;
        java.lang.Float vmConfidence1 = null;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> vmGetSampleCollectionLongPressed = null;
        java.util.Map<java.lang.String,java.lang.Float> vmConfidenceGetValue = null;
        java.lang.Integer vmNumSamples2 = null;
        boolean vmFirstChoiceJavaLangString2 = false;

        if ((dirtyFlags & 0x7ffL) != 0) {


            if ((dirtyFlags & 0x601L) != 0) {

                    if (vm != null) {
                        // read vm.numCollectedSamples
                        vmNumCollectedSamples = vm.getNumCollectedSamples();
                    }
                    updateLiveDataRegistration(0, vmNumCollectedSamples);


                    if (vmNumCollectedSamples != null) {
                        // read vm.numCollectedSamples.getValue()
                        vmNumCollectedSamplesGetValue = vmNumCollectedSamples.getValue();
                    }


                    // read String.format("%d samples collected", vm.numCollectedSamples.getValue())
                    stringFormatJavaLangStringDSamplesCollectedVmNumCollectedSamples = java.lang.String.format("%d samples collected", vmNumCollectedSamplesGetValue);
            }
            if ((dirtyFlags & 0x622L) != 0) {

                    if (vm != null) {
                        // read vm.neededSamples
                        vmNeededSamples = vm.getNeededSamples();
                    }
                    updateLiveDataRegistration(1, vmNeededSamples);


                    if (vmNeededSamples != null) {
                        // read vm.neededSamples.getValue()
                        vmNeededSamplesGetValue = vmNeededSamples.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxVmNeededSamplesGetValue = androidx.databinding.ViewDataBinding.safeUnbox(vmNeededSamplesGetValue);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) == 0
                    vmNeededSamplesInt0 = (androidxDatabindingViewDataBindingSafeUnboxVmNeededSamplesGetValue) == (0);
                if((dirtyFlags & 0x622L) != 0) {
                    if(vmNeededSamplesInt0) {
                            dirtyFlags |= 0x40000L;
                    }
                    else {
                            dirtyFlags |= 0x20000L;
                    }
                }
            }
            if ((dirtyFlags & 0x654L) != 0) {

                    if (vm != null) {
                        // read vm.confidence
                        vmConfidence = vm.getConfidence();
                        // read vm.numSamples
                        vmNumSamples = vm.getNumSamples();
                    }
                    updateLiveDataRegistration(2, vmConfidence);
                    updateLiveDataRegistration(6, vmNumSamples);


                    if (vmConfidence != null) {
                        // read vm.confidence.getValue()
                        vmConfidenceGetValue = vmConfidence.getValue();
                    }
                    if (vmNumSamples != null) {
                        // read vm.numSamples.getValue()
                        vmNumSamplesGetValue = vmNumSamples.getValue();
                    }


                    if (vmConfidenceGetValue != null) {
                        // read vm.confidence.getValue()["4"]
                        vmConfidence4 = vmConfidenceGetValue.get("4");
                        // read vm.confidence.getValue()["2"]
                        vmConfidence2 = vmConfidenceGetValue.get("2");
                        // read vm.confidence.getValue()["3"]
                        vmConfidence3 = vmConfidenceGetValue.get("3");
                        // read vm.confidence.getValue()["1"]
                        vmConfidence1 = vmConfidenceGetValue.get("1");
                    }
                    if (vmNumSamplesGetValue != null) {
                        // read vm.numSamples.getValue()["3"]
                        vmNumSamples3 = vmNumSamplesGetValue.get("3");
                        // read vm.numSamples.getValue()["4"]
                        vmNumSamples4 = vmNumSamplesGetValue.get("4");
                        // read vm.numSamples.getValue()["1"]
                        vmNumSamples1 = vmNumSamplesGetValue.get("1");
                        // read vm.numSamples.getValue()["2"]
                        vmNumSamples2 = vmNumSamplesGetValue.get("2");
                    }
            }
            if ((dirtyFlags & 0x65eL) != 0) {

                    if (vm != null) {
                        // read vm.captureMode
                        VmCaptureMode1 = vm.getCaptureMode();
                    }
                    updateLiveDataRegistration(4, VmCaptureMode1);


                    if (VmCaptureMode1 != null) {
                        // read vm.captureMode.getValue()
                        vmCaptureModeGetValue = VmCaptureMode1.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue = androidx.databinding.ViewDataBinding.safeUnbox(vmCaptureModeGetValue);
                if((dirtyFlags & 0x612L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue) {
                            dirtyFlags |= 0x10000L;
                    }
                    else {
                            dirtyFlags |= 0x8000L;
                    }
                }

                if ((dirtyFlags & 0x618L) != 0) {

                        // read !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue())
                        vmCaptureMode = !androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue;
                    if((dirtyFlags & 0x618L) != 0) {
                        if(vmCaptureMode) {
                                dirtyFlags |= 0x1000L;
                                dirtyFlags |= 0x4000L;
                                dirtyFlags |= 0x100000L;
                                dirtyFlags |= 0x400000L;
                        }
                        else {
                                dirtyFlags |= 0x800L;
                                dirtyFlags |= 0x2000L;
                                dirtyFlags |= 0x80000L;
                                dirtyFlags |= 0x200000L;
                        }
                    }
                }
                if ((dirtyFlags & 0x654L) != 0) {
                }
            }
            if ((dirtyFlags & 0x620L) != 0) {

                    if (vm != null) {
                        // read vm.trainingState
                        vmTrainingState = vm.getTrainingState();
                    }
                    updateLiveDataRegistration(5, vmTrainingState);


                    if (vmTrainingState != null) {
                        // read vm.trainingState.getValue()
                        vmTrainingStateGetValue = vmTrainingState.getValue();
                    }


                    // read vm.trainingState.getValue() == TrainingState.PAUSED
                    vmTrainingStateTrainingStatePAUSED = (vmTrainingStateGetValue) == (org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.PAUSED);
                    // read vm.trainingState.getValue() == TrainingState.STARTED
                    vmTrainingStateTrainingStateSTARTED = (vmTrainingStateGetValue) == (org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.STARTED);
            }
            if ((dirtyFlags & 0x680L) != 0) {

                    if (vm != null) {
                        // read vm.lastLoss
                        vmLastLoss = vm.getLastLoss();
                    }
                    updateLiveDataRegistration(7, vmLastLoss);


                    if (vmLastLoss != null) {
                        // read vm.lastLoss.getValue()
                        vmLastLossGetValue = vmLastLoss.getValue();
                    }


                    // read vm.lastLoss.getValue() != null
                    vmLastLossJavaLangObjectNull = (vmLastLossGetValue) != (null);
                    // read String.format("Loss: %.3f", vm.lastLoss.getValue())
                    stringFormatJavaLangStringLoss3fVmLastLoss = java.lang.String.format("Loss: %.3f", vmLastLossGetValue);
            }
            if ((dirtyFlags & 0x700L) != 0) {

                    if (vm != null) {
                        // read vm.getSampleCollectionLongPressed
                        vmGetSampleCollectionLongPressed = vm.getSampleCollectionLongPressed();
                    }
                    updateLiveDataRegistration(8, vmGetSampleCollectionLongPressed);


                    if (vmGetSampleCollectionLongPressed != null) {
                        // read vm.getSampleCollectionLongPressed.getValue()
                        vmGetSampleCollectionLongPressedGetValue = vmGetSampleCollectionLongPressed.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(vm.getSampleCollectionLongPressed.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxVmGetSampleCollectionLongPressedGetValue = androidx.databinding.ViewDataBinding.safeUnbox(vmGetSampleCollectionLongPressedGetValue);
            }
        }
        // batch finished

        if ((dirtyFlags & 0x10000L) != 0) {

                if (vm != null) {
                    // read vm.neededSamples
                    vmNeededSamples = vm.getNeededSamples();
                }
                updateLiveDataRegistration(1, vmNeededSamples);


                if (vmNeededSamples != null) {
                    // read vm.neededSamples.getValue()
                    vmNeededSamplesGetValue = vmNeededSamples.getValue();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue())
                androidxDatabindingViewDataBindingSafeUnboxVmNeededSamplesGetValue = androidx.databinding.ViewDataBinding.safeUnbox(vmNeededSamplesGetValue);


                // read androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) > 0
                VmNeededSamplesInt01 = (androidxDatabindingViewDataBindingSafeUnboxVmNeededSamplesGetValue) > (0);
        }
        if ((dirtyFlags & 0x505000L) != 0) {

                if (vm != null) {
                    // read vm.firstChoice
                    vmFirstChoice = vm.getFirstChoice();
                }
                updateLiveDataRegistration(3, vmFirstChoice);


                if (vmFirstChoice != null) {
                    // read vm.firstChoice.getValue()
                    vmFirstChoiceGetValue = vmFirstChoice.getValue();
                }

            if ((dirtyFlags & 0x400000L) != 0) {

                    // read vm.firstChoice.getValue() == "4"
                    vmFirstChoiceJavaLangString4 = (vmFirstChoiceGetValue) == ("4");
            }
            if ((dirtyFlags & 0x4000L) != 0) {

                    // read vm.firstChoice.getValue() == "3"
                    vmFirstChoiceJavaLangString3 = (vmFirstChoiceGetValue) == ("3");
            }
            if ((dirtyFlags & 0x1000L) != 0) {

                    // read vm.firstChoice.getValue() == "1"
                    vmFirstChoiceJavaLangString1 = (vmFirstChoiceGetValue) == ("1");
            }
            if ((dirtyFlags & 0x100000L) != 0) {

                    // read vm.firstChoice.getValue() == "2"
                    vmFirstChoiceJavaLangString2 = (vmFirstChoiceGetValue) == ("2");
            }
        }
        if ((dirtyFlags & 0x40000L) != 0) {

                if (vm != null) {
                    // read vm.trainingState
                    vmTrainingState = vm.getTrainingState();
                }
                updateLiveDataRegistration(5, vmTrainingState);


                if (vmTrainingState != null) {
                    // read vm.trainingState.getValue()
                    vmTrainingStateGetValue = vmTrainingState.getValue();
                }


                // read vm.trainingState.getValue() == TrainingState.NOT_STARTED
                vmTrainingStateTrainingStateNOTSTARTED = (vmTrainingStateGetValue) == (org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.NOT_STARTED);
        }

        if ((dirtyFlags & 0x618L) != 0) {

                // read !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "1" : false
                vmCaptureModeVmFirstChoiceJavaLangString1BooleanFalse = ((vmCaptureMode) ? (vmFirstChoiceJavaLangString1) : (false));
                // read !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "3" : false
                vmCaptureModeVmFirstChoiceJavaLangString3BooleanFalse = ((vmCaptureMode) ? (vmFirstChoiceJavaLangString3) : (false));
                // read !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "2" : false
                vmCaptureModeVmFirstChoiceJavaLangString2BooleanFalse = ((vmCaptureMode) ? (vmFirstChoiceJavaLangString2) : (false));
                // read !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "4" : false
                vmCaptureModeVmFirstChoiceJavaLangString4BooleanFalse = ((vmCaptureMode) ? (vmFirstChoiceJavaLangString4) : (false));
        }
        if ((dirtyFlags & 0x612L) != 0) {

                // read androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) > 0 : false
                vmCaptureModeVmNeededSamplesInt0BooleanFalse = ((androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue) ? (VmNeededSamplesInt01) : (false));
        }
        if ((dirtyFlags & 0x622L) != 0) {

                // read androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) == 0 ? vm.trainingState.getValue() == TrainingState.NOT_STARTED : false
                vmNeededSamplesInt0VmTrainingStateTrainingStateNOTSTARTEDBooleanFalse = ((vmNeededSamplesInt0) ? (vmTrainingStateTrainingStateNOTSTARTED) : (false));
        }
        // batch finished
        if ((dirtyFlags & 0x618L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setClassButtonHighlight(this.classBtn1, vmCaptureModeVmFirstChoiceJavaLangString1BooleanFalse);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassButtonHighlight(this.classBtn2, vmCaptureModeVmFirstChoiceJavaLangString2BooleanFalse);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassButtonHighlight(this.classBtn3, vmCaptureModeVmFirstChoiceJavaLangString3BooleanFalse);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassButtonHighlight(this.classBtn4, vmCaptureModeVmFirstChoiceJavaLangString4BooleanFalse);
        }
        if ((dirtyFlags & 0x610L) != 0) {
            // api target 1

            this.classBtn1.setClickable(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn1.setEnabled(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn2.setClickable(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn2.setEnabled(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn3.setClickable(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn3.setEnabled(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn4.setClickable(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
            this.classBtn4.setEnabled(androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue);
        }
        if ((dirtyFlags & 0x700L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView1, androidxDatabindingViewDataBindingSafeUnboxVmGetSampleCollectionLongPressedGetValue);
        }
        if ((dirtyFlags & 0x654L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setClassSubtitleText(this.mboundView10, androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue, vmConfidence1, vmNumSamples1);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassSubtitleText(this.mboundView12, androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue, vmConfidence2, vmNumSamples2);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassSubtitleText(this.mboundView14, androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue, vmConfidence3, vmNumSamples3);
            org.tensorflow.lite.examples.transfer.CameraFragment.setClassSubtitleText(this.mboundView16, androidxDatabindingViewDataBindingSafeUnboxVmCaptureModeGetValue, vmConfidence4, vmNumSamples4);
        }
        if ((dirtyFlags & 0x601L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, stringFormatJavaLangStringDSamplesCollectedVmNumCollectedSamples);
        }
        if ((dirtyFlags & 0x612L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView3, vmCaptureModeVmNeededSamplesInt0BooleanFalse);
        }
        if ((dirtyFlags & 0x620L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView5, vmTrainingStateTrainingStateSTARTED);
            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView7, vmTrainingStateTrainingStatePAUSED);
        }
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.mboundView5.setOnClickListener(mCallback2);
            this.mboundView7.setOnClickListener(mCallback3);
            this.trainStartButton.setOnClickListener(mCallback1);
        }
        if ((dirtyFlags & 0x680L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView6, vmLastLossJavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, stringFormatJavaLangStringLoss3fVmLastLoss);
            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.mboundView8, vmLastLossJavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, stringFormatJavaLangStringLoss3fVmLastLoss);
        }
        if ((dirtyFlags & 0x622L) != 0) {
            // api target 1

            org.tensorflow.lite.examples.transfer.CameraFragment.setViewVisibility(this.trainStartButton, vmNeededSamplesInt0VmTrainingStateTrainingStateNOTSTARTEDBooleanFalse);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // vm != null
                boolean vmJavaLangObjectNull = false;
                // vm
                org.tensorflow.lite.examples.transfer.CameraFragmentViewModel vm = mVm;



                vmJavaLangObjectNull = (vm) != (null);
                if (vmJavaLangObjectNull) {




                    vm.setTrainingState(org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.PAUSED);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // vm != null
                boolean vmJavaLangObjectNull = false;
                // vm
                org.tensorflow.lite.examples.transfer.CameraFragmentViewModel vm = mVm;



                vmJavaLangObjectNull = (vm) != (null);
                if (vmJavaLangObjectNull) {




                    vm.setTrainingState(org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.STARTED);
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // vm != null
                boolean vmJavaLangObjectNull = false;
                // vm
                org.tensorflow.lite.examples.transfer.CameraFragmentViewModel vm = mVm;



                vmJavaLangObjectNull = (vm) != (null);
                if (vmJavaLangObjectNull) {




                    vm.setTrainingState(org.tensorflow.lite.examples.transfer.CameraFragmentViewModel.TrainingState.STARTED);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): vm.numCollectedSamples
        flag 1 (0x2L): vm.neededSamples
        flag 2 (0x3L): vm.confidence
        flag 3 (0x4L): vm.firstChoice
        flag 4 (0x5L): vm.captureMode
        flag 5 (0x6L): vm.trainingState
        flag 6 (0x7L): vm.numSamples
        flag 7 (0x8L): vm.lastLoss
        flag 8 (0x9L): vm.getSampleCollectionLongPressed
        flag 9 (0xaL): vm
        flag 10 (0xbL): null
        flag 11 (0xcL): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "1" : false
        flag 12 (0xdL): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "1" : false
        flag 13 (0xeL): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "3" : false
        flag 14 (0xfL): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "3" : false
        flag 15 (0x10L): androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) > 0 : false
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) > 0 : false
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) == 0 ? vm.trainingState.getValue() == TrainingState.NOT_STARTED : false
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(vm.neededSamples.getValue()) == 0 ? vm.trainingState.getValue() == TrainingState.NOT_STARTED : false
        flag 19 (0x14L): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "2" : false
        flag 20 (0x15L): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "2" : false
        flag 21 (0x16L): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "4" : false
        flag 22 (0x17L): !androidx.databinding.ViewDataBinding.safeUnbox(vm.captureMode.getValue()) ? vm.firstChoice.getValue() == "4" : false
    flag mapping end*/
    //end
}