package idlework.eegreader.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import idlework.eegreader.R;
import idlework.eegreader.activities.contracts.ActivityConnectContract;
import idlework.eegreader.activities.controllers.ConnectController;
import idlework.eegreader.generics.GenericActivity;
import idlework.eegreader.generics.GenericApplication;
import idlework.eegreader.generics.contracts.GenericActivitySignalContract;
import idlework.eegreader.neurosky.EEGDeviceHandler;
import idlework.eegreader.utils.LogUtils;
import idlework.eegreader.utils.StringUtils;

public class ConnectActivity extends GenericActivity implements ActivityConnectContract, GenericActivitySignalContract {

  private ConnectController connectController;
  public MainActivity mainobject;
  private Handler nextActivityHandler;
  private Button next;

    private static final String TAG = "think2play"; //sophi
    ArrayList<Integer> med = new ArrayList(); //soph
    ArrayList<Integer> att = new ArrayList(); //soph
    public static int count =0;
    public static int count2 =0;
    public static int sum =0;
    public static int sum2 =0;
    public static int avg =0;
    public static int avg2 =0;


    private ImageView bluetoothConnect;
  private TextView labelConnect;
  private TextView alphalevel,betalevel,blinklevel;
  private Runnable startNextActivity = new Runnable() {
    @Override
    public void run() {
      finish();
      //labelConnect.setText("alpha level displayed");
       startActivity(new Intent(GenericApplication.getContext(), MainActivity.class));
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_connect);

    nextActivityHandler = new Handler();
    bluetoothConnect = (ImageView) findViewById(R.id.bluetooth_connect);
    labelConnect = (TextView) findViewById(R.id.label_connect);
    alphalevel = (TextView)findViewById(R.id.alphahigh);
    betalevel = (TextView)findViewById(R.id.betahigh);
    blinklevel = (TextView)findViewById(R.id.blinklevel);
    next = (Button)findViewById(R.id.next);

  }

    public void clicknext(View view)
    {
  //connectController.establishConnection(" ", startNextActivity);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
}
  @Override
  protected void onResume() {
    super.onResume();

    connectController = new ConnectController(this);

    if (!GenericApplication.getEegDeviceUtils().initializeBlueToothAdapter()) {
      Toast.makeText(this, StringUtils.getStringFromResources(R.string.bluetooth_is_not_available), Toast.LENGTH_LONG).show();
    } else {
      GenericApplication.getEegDeviceUtils().setActivityViewContract(this);
    }
  }

  public void bluetoothConnectClickHandler(View view) {
    if (GenericApplication.getEegDeviceUtils().isBluetoothTurnedOn()) {
      GenericApplication.getEegDeviceUtils().connectToDevice();
    } else {
      Toast.makeText(this, StringUtils.getStringFromResources(R.string.please_activate_bluetooth), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public ImageView getBluetoothConnect() {
    return bluetoothConnect;
  }

  @Override
  public Handler getNextActivityHandler() {
    return nextActivityHandler;
  }

  @Override
  public TextView getLabel() {
    return labelConnect;
  }

  @Override
  public void setMessageFromDevice(String string) {
    labelConnect.setText(string);
   // connectController.establishConnection(string, startNextActivity);
  }

  @Override
  public void setSignalLevel(int level) {

  }

  @Override
  public void setAttentionLevel(int level) {
    betalevel.setText("loading...");

      att.add(level);
      count2++;
      if(count2==20) {
          sum2 = 0;
          for (int i = 1; i < att.size(); i++) {
              sum2 += att.get(i);
              Log.d(TAG, "Att Arraylist:" + att.get(i));
          }
          avg2=sum2/att.size();
          Log.d(TAG, "outcome = " + sum2);
          Log.d(TAG, "att average = " + avg2);

          att.clear();
      }
    betalevel.setText(String.valueOf(level));
  }

  @Override
  public void setMeditationLevel(int level) {

      alphalevel.setText("loading...");

      med.add(level);
      count++;


      alphalevel.setText(String.valueOf(level));

      if(count==20) {
          sum = 0;
          for (int i = 1; i < med.size(); i++) {
              sum += med.get(i);
              Log.d(TAG, "Med Arraylist:" + med.get(i));
          }
          avg=sum/med.size();
          Log.d(TAG, "outcome = " + sum);
          Log.d(TAG, "med average = " + avg);

          med.clear();
      }
  }

  @Override
  public void setBlinkLevel(int level)

  {

      blinklevel.setText("loading...");
      blinklevel.setText(String.valueOf(level));
  }

  @Override
  public void setRawData(int level) {

  }

  @Override
  public void setDelta(int level) {

  }

  @Override
  public void setTheta(int level) {

  }

  @Override
  public void setLowAlpha(int level) {

  }

  @Override
  public void setHighAlpha(int level)
  {
      //alphalevel.setText(String.valueOf(level));
  }

  @Override
  public void setLowBeta(int level) {

  }

  @Override
  public void setHighBeta(int level) {

  }

  @Override
  public void setLowGamma(int level) {

  }

  @Override
  public void setMidGamma(int level) {

  }
}
