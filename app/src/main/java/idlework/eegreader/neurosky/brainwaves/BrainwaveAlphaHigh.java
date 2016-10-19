package idlework.eegreader.neurosky.brainwaves;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import idlework.eegreader.R;
import idlework.eegreader.neurosky.EEGDeviceHandler;
import idlework.eegreader.neurosky.GenericSignal;
import idlework.eegreader.neurosky.SignalSettable;
import idlework.eegreader.utils.LogUtils;

public class BrainwaveAlphaHigh extends GenericSignal implements Runnable, SignalSettable {
  private static final String TAG = LogUtils.makeLogTag(BrainwaveAlphaHigh.class);


  @Override
  public void run() {
      EEGDeviceHandler.getActivityViewContract().setHighAlpha(level);
    LogUtils.LOGD(TAG, "Control signal - High Alpha wave: " + level);
  }
}