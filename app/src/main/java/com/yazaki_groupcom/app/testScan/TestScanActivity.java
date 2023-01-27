package com.yazaki_groupcom.app.testScan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unitech.api.apn.ApnCtrl;
import com.unitech.api.app.AppManagementCtrl;
import com.unitech.api.audio.AudioCtrl;
import com.unitech.api.clock.ClockCtrl;
import com.unitech.api.debugging.DebuggingCtrl;
import com.unitech.api.display.DisplayCtrl;
import com.unitech.api.dmi.DmiCtrl;
import com.unitech.api.file.FileCtrl;
import com.unitech.api.fota.FotaCtrl;
import com.unitech.api.general.GeneralCtrl;
import com.unitech.api.keymap.KeymappingCtrl;
import com.unitech.api.location.LocationCtrl;
import com.unitech.api.nfc.NfcCtrl;
import com.unitech.api.power.PowerCtrl;
import com.unitech.api.safemodelock.SafeModeLockCtrl;
import com.unitech.api.scanner.ScannerCtrl;
import com.unitech.api.security.SecurityCtrl;
import com.unitech.api.uapps.ElauncherCtrl;
import com.unitech.api.uapps.MoboLinkCtrl;
import com.unitech.api.uapps.RFID2KeyCtrl;
import com.unitech.api.uapps.SoftwareUpdateCtrl;
import com.unitech.api.uapps.StageGoCtrl;
import com.unitech.api.usu.USUCtrl;
import com.unitech.api.wlan.WlanAdvancedCtrl;
import com.unitech.api.wlan.WlanCtrl;
import com.yazaki_groupcom.app.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * UnitechSDK_Tester_v1.1.17 官方的测试app
 */
public class TestScanActivity extends Activity{

//region Apn Columns
    final String[] COLUMNS_OF_APN = {
            "_id",
            "name",
            "numeric",
            "mcc",
            "mnc",
            "apn",
            "user",
            "server",
            "password",
            "proxy",
            "port",
            "mmsproxy",
            "mmsport",
            "mmsc",
            "authtype",
            "type",
            "current",
            "protocol",
            "roaming_protocol",
            "carrier_enabled",
            "bearer",
            "mvno_type",
            "mvno_match_data"
    };  //totally 22 items without id
//endregion

//region command defined
    // analytics

    // region Apn 11
    public static final int ApnAddProfile = 1;
    public static final int ApnDeleteProfile = ApnAddProfile + 1;
    public static final int ApnProfileIsExist = ApnDeleteProfile + 1;
    public static final int ApnUpdateProfile = ApnProfileIsExist + 1;
    public static final int ApnDeleteAll = ApnUpdateProfile + 1;
    public static final int ApnSetActiveProfile = ApnDeleteAll + 1;
    public static final int ApnGetActiveProfileApnName = ApnSetActiveProfile + 1;
    public static final int ApnGetNameList = ApnGetActiveProfileApnName + 1;
    public static final int ApnGetProfile = ApnGetNameList + 1;
    public static final int setAirplaneMode_Enable = ApnGetProfile + 1;
    public static final int setAirplaneMode_Disable = setAirplaneMode_Enable + 1;
    //endregion

    // region AppManagement 22
    public static final int installApp = setAirplaneMode_Disable + 1;
    public static final int removeApp = installApp + 1;
    public static final int activateApp = removeApp + 1;
    public static final int deactivateApp = activateApp + 1;
    public static final int enableApp = deactivateApp + 1;
    public static final int disableApp = enableApp + 1;
    public static final int clearAppData = disableApp + 1;
    public static final int getRunningAppNameList = clearAppData + 1;
    public static final int getRunningPkgNameList = getRunningAppNameList + 1;
    public static final int getInstallAppNameList = getRunningPkgNameList + 1;
    public static final int getInstallPkgNameList = getInstallAppNameList + 1;
    public static final int getBuiltinSystemAppNameList = getInstallPkgNameList + 1;
    public static final int getBuiltinSystemPkgNameList = getBuiltinSystemAppNameList + 1;
    public static final int getDisabledAppNameList = getBuiltinSystemPkgNameList + 1;
    public static final int getDisabledPkgNameList = getDisabledAppNameList + 1;
    public static final int osUpdate = getDisabledPkgNameList + 1;
    public static final int runSysCmd = osUpdate + 1;
    public static final int getAppInfoByAppName = runSysCmd + 1;
    public static final int getAppInfoByPkgName = getAppInfoByAppName + 1;
    public static final int setDefaultApp = getAppInfoByPkgName + 1;
    public static final int clearDefaultApps_with_PackageNameArray = setDefaultApp + 1;
    public static final int clearDefaultApps_Action_CategoriesArray = clearDefaultApps_with_PackageNameArray + 1;
    // endregion

    // region Audio 8
    public static final int setDefaultNotificationSound = clearDefaultApps_Action_CategoriesArray +1;
    public static final int setRingtoneSound = setDefaultNotificationSound + 1;
    public static final int setDialPadTouchTones_Enable = setRingtoneSound + 1;
    public static final int setDialPadTouchTones_Disable = setDialPadTouchTones_Enable + 1;
    public static final int setTouchSound_Enable = setDialPadTouchTones_Disable + 1;
    public static final int setTouchSound_Disable = setTouchSound_Enable + 1;
    public static final int setVibrateOnTouch_Enable = setTouchSound_Disable + 1;
    public static final int setVibrateOnTouch_Disable = setVibrateOnTouch_Enable + 1;
    public static final int setVolume = setVibrateOnTouch_Disable + 1;

    // endregion

    // region clock 16
    public static final int setNTPServer = setVolume + 1;
    public static final int getNTPServer = setNTPServer + 1;
    public static final int setTimeMode_Enable = getNTPServer + 1;
    public static final int setTimeMode_Disable = setTimeMode_Enable + 1;
    public static final int getTimeMode = setTimeMode_Disable + 1;
    public static final int setManualDate = getTimeMode + 1;
    public static final int setManualTime = setManualDate + 1;
    public static final int getManualTime = setManualTime + 1;
    public static final int setTimeZoneMode_Enable = getManualTime + 1;
    public static final int setTimeZoneMode_Disable = setTimeZoneMode_Enable + 1;
    public static final int getTimeZoneMode = setTimeZoneMode_Disable + 1;
    public static final int setTimeZone = getTimeZoneMode + 1;
    public static final int getTimeZone = setTimeZone + 1;
    public static final int setTimeFormat_24 = getTimeZone + 1;
    public static final int setTimeFormat_12 = setTimeFormat_24 + 1;
    public static final int getTimeFormat = setTimeFormat_12 + 1;
    // endregion

    // region debugging 2
    public static final int saveLogcatFile = getTimeFormat + 1;
    public static final int stopSaveLogcat = saveLogcatFile + 1;
    // endregion

    // region display 17
    public static final int SetDisplayTimeout = stopSaveLogcat + 1;
    public static final int GetDisplayTimeout = SetDisplayTimeout + 1;
    public static final int SetStayAwake_Enable = GetDisplayTimeout + 1;
    public static final int SetStayAwake_Disable = SetStayAwake_Enable + 1;
    public static final int GetStayAwake = SetStayAwake_Disable + 1;
    public static final int SetFontSize = GetStayAwake + 1;
    public static final int GetFontSize = SetFontSize + 1;
    public static final int SetScreenBacklightLevel = GetFontSize + 1;
    public static final int GetScreenBacklightLevel = SetScreenBacklightLevel + 1;
    public static final int SetAutoBrightness_Enable = GetScreenBacklightLevel + 1;
    public static final int SetAutoBrightness_Disable = SetAutoBrightness_Enable + 1;
    public static final int DisableScreenLock = SetAutoBrightness_Disable + 1;
    public static final int SetAutoRotation_Enable = DisableScreenLock + 1;
    public static final int SetAutoRotation_Disable = SetAutoRotation_Enable + 1;
    public static final int setDisplayBatteryPercentage_Enable = SetAutoRotation_Disable + 1;
    public static final int setDisplayBatteryPercentage_Disable = setDisplayBatteryPercentage_Enable + 1;
    public static final int SetGloveMode_Enable = setDisplayBatteryPercentage_Disable + 1;
    public static final int SetGloveMode_Disable = SetGloveMode_Enable + 1;
    public static final int GetGloveMode = SetGloveMode_Disable + 1;
    public static final int ShowLockScreenNotification = GetGloveMode + 1;
    public static final int HideLockScreenNotification = ShowLockScreenNotification + 1;
    public static final int SetScreenOrientationDisable = HideLockScreenNotification + 1;
    public static final int SetScreenOrientationPortrait = SetScreenOrientationDisable + 1;
    public static final int SetScreenOrientationLandscape = SetScreenOrientationPortrait + 1;
    public static final int SetScreenOrientationReversePortrait = SetScreenOrientationLandscape + 1;
    public static final int SetScreenOrientationReverseLandscape = SetScreenOrientationReversePortrait + 1;
    public static final int SetScreenOrientationAuto = SetScreenOrientationReverseLandscape + 1;
    // endregion

    // region dmi 39
    public static final int HW_Flash_Enable = SetScreenOrientationAuto + 1;
    public static final int HW_Flash_Disable = HW_Flash_Enable + 1;
    public static final int HW_Flash_Get_Status = HW_Flash_Disable + 1;
    public static final int HW_Camera_Enable = HW_Flash_Get_Status + 1;
    public static final int HW_Camera_Disable = HW_Camera_Enable + 1;
    public static final int HW_Camera_Get_Status = HW_Camera_Disable + 1;
    public static final int HW_Keyboard_Enable = HW_Camera_Get_Status + 1;
    public static final int HW_Keyboard_Disable = HW_Keyboard_Enable + 1;
    public static final int HW_Keyboard_Get_Status = HW_Keyboard_Disable + 1;
    public static final int HW_Usb_Enable = HW_Keyboard_Get_Status + 1;
    public static final int HW_Usb_Disable = HW_Usb_Enable + 1;
    public static final int HW_Usb_Get_Status = HW_Usb_Disable + 1;
    public static final int HW_Usb_MTP_Mode_Enable = HW_Usb_Get_Status + 1;
    public static final int HW_Usb_MTP_Mode_Disable = HW_Usb_MTP_Mode_Enable + 1;
    public static final int HW_Usb_MTP_Mode_Get_Status = HW_Usb_MTP_Mode_Disable + 1;
    public static final int HW_Gps_Enable = HW_Usb_MTP_Mode_Get_Status + 1;
    public static final int HW_Gps_Disable = HW_Gps_Enable + 1;
    public static final int HW_Gps_Get_Status = HW_Gps_Disable + 1;
    public static final int HW_WWAN_Enable = HW_Gps_Get_Status + 1;
    public static final int HW_WWAN_Disable = HW_WWAN_Enable + 1;
    public static final int HW_WWAN_Get_Status = HW_WWAN_Disable + 1;
    public static final int HW_Scanner_Enable = HW_WWAN_Get_Status + 1;
    public static final int HW_Scanner_Disable = HW_Scanner_Enable + 1;
    public static final int HW_Scanner_Get_Status = HW_Scanner_Disable + 1;
    public static final int HW_Ime_Enable = HW_Scanner_Get_Status + 1;
    public static final int HW_Ime_Disable = HW_Ime_Enable + 1;
    public static final int HW_Ime_Get_Status = HW_Ime_Disable + 1;
    public static final int HW_WLAN_Enable = HW_Ime_Get_Status + 1;
    public static final int HW_WLAN_Disable = HW_WLAN_Enable + 1;
    public static final int HW_WLAN_Get_Status = HW_WLAN_Disable + 1;
    public static final int HW_Bluetooth_Enable = HW_WLAN_Get_Status + 1;
    public static final int HW_Bluetooth_Disable = HW_Bluetooth_Enable + 1;
    public static final int HW_Bluetooth_Get_Status = HW_Bluetooth_Disable + 1;
    public static final int HW_Touch_Enable = HW_Bluetooth_Get_Status + 1;
    public static final int HW_Touch_Disable = HW_Touch_Enable + 1;
    public static final int HW_Touch_Get_Status = HW_Touch_Disable + 1;
    public static final int HW_DozeMode_Enable = HW_Touch_Get_Status + 1;
    public static final int HW_DozeMode_Disable = HW_DozeMode_Enable + 1;
    public static final int HW_DozeMode_Get_Status = HW_DozeMode_Disable + 1;
    // endregion

    // region file 8
    public static final int writeUTF8ToFile = HW_DozeMode_Get_Status + 1;
    public static final int writeToFile = writeUTF8ToFile + 1;
    public static final int readFromFile = writeToFile + 1;
    public static final int copyFile = readFromFile + 1;
    public static final int createFile = copyFile + 1;
    public static final int deleteFile = createFile + 1;
    public static final int createFileStream = deleteFile + 1;
    public static final int exists = createFileStream + 1;
    public static final int isDirectory = exists + 1;
    public static final int list = isDirectory + 1;
    public static final int openFile = list + 1;
    public static final int renameTo = openFile + 1;
    public static final int canWrite = renameTo + 1;
    public static final int canRead = canWrite + 1;
    // endregion

    // region fota 1
    public static final int silentOSUpdate = canRead + 1;
    // endregion

    // region general 2
    public static final int setLanguage = silentOSUpdate + 1;
    public static final int setImeCurrentKeyboard = setLanguage + 1;
    public static final int getDeviceSerialNumber = setImeCurrentKeyboard + 1;
    public static final int getDeviceBuildNumber = getDeviceSerialNumber + 1;
    // endregion

    // region keymap 7
    public static final int enableKeyMapping_Enable = getDeviceBuildNumber + 1;
    public static final int enableKeyMapping_Disable = enableKeyMapping_Enable + 1;
    public static final int addKeyMappings_Keycode = enableKeyMapping_Disable + 1;
    public static final int addKeyMappings_LaunchApp = addKeyMappings_Keycode + 1;
    public static final int resetKeyMappings = addKeyMappings_LaunchApp + 1;
    public static final int importKeyMappings = resetKeyMappings + 1;
    public static final int exportKeyMappings = importKeyMappings + 1;
    // endregion

    // region location 2
    public static final int SW_setLocationMode_Enable = exportKeyMappings + 1;
    public static final int SW_setLocationMode_Disable = SW_setLocationMode_Enable + 1;
    // endregion

    // region nfc 2
    public static final int SW_nfc_Enable = SW_setLocationMode_Disable + 1;
    public static final int SW_nfc_Disable = SW_nfc_Enable + 1;
    // endregion

    // region power 4
    public static final int ColdBoot = SW_nfc_Disable + 1;
    public static final int WarmBoot = ColdBoot + 1;
    public static final int acquireWakeLock = WarmBoot + 1;
    public static final int releaseWakeLock = acquireWakeLock + 1;
    // endregion

    // region safemodelock 1
    public static final int setSafeModeLock = releaseWakeLock + 1;
    // endregion

    // region scanner 4
    public static final int importScannerSettings = setSafeModeLock + 1;
    public static final int exportScannerSettings = importScannerSettings + 1;
    public static final int importEaSeriesScannerSettings = exportScannerSettings + 1;
    public static final int exportEaSeriesScannerSettings = importEaSeriesScannerSettings + 1;
    // endregion

    // region uapps 23
    public static final int Elauncher_setEnabled = exportEaSeriesScannerSettings + 1;
    public static final int Elauncher_setKioskApp = Elauncher_setEnabled + 1;
    public static final int Elauncher_setLauncherAppList = Elauncher_setKioskApp + 1;
    public static final int Elauncher_setWallpaper = Elauncher_setLauncherAppList + 1;
    public static final int Elauncher_setPINCode = Elauncher_setWallpaper + 1;
    public static final int Elauncher_setMode  = Elauncher_setPINCode + 1;
    public static final int MoboLink_SetServerURL = Elauncher_setMode + 1;
    public static final int MoboLink_SetRebootstrap = MoboLink_SetServerURL + 1;
    public static final int MoboLink_SetShowUI = MoboLink_SetRebootstrap + 1;
    public static final int MoboLink_SetConnectionNotification = MoboLink_SetShowUI + 1;
    public static final int MoboLink_SetEventNotification = MoboLink_SetConnectionNotification + 1;
    public static final int MoboLink_SetUserConfigurable = MoboLink_SetEventNotification + 1;
    public static final int MoboLink_SetAutoRun = MoboLink_SetUserConfigurable + 1;
    public static final int MoboLink_SetMoboLinkConfig = MoboLink_SetAutoRun + 1;
    public static final int SoftwareUpdate_updateConfig = MoboLink_SetMoboLinkConfig + 1;
    public static final int SoftwareUpdate_exportSettings = SoftwareUpdate_updateConfig + 1;
    public static final int SoftwareUpdate_importSettings = SoftwareUpdate_exportSettings + 1;
    public static final int SoftwareUpdate_resetSettings = SoftwareUpdate_importSettings + 1;
    public static final int StageGo_setReportPath = SoftwareUpdate_resetSettings + 1;
    public static final int StageGo_setReportMaxNum = StageGo_setReportPath + 1;
    public static final int StageGo_enabledBootupScript = StageGo_setReportMaxNum + 1;
    public static final int StageGo_setBootupScript = StageGo_enabledBootupScript + 1;
    public static final int StageGo_setPasscode = StageGo_setBootupScript + 1;
    public static final int StageGo_setScanMode = StageGo_setPasscode + 1;
    public static final int RFID2Key_importSettings = StageGo_setScanMode + 1;
    public static final int RFID2Key_exportSettings = RFID2Key_importSettings + 1;
    public static final int RFID2Key_resetSettings = RFID2Key_exportSettings + 1;
    public static final int RFID2Key_setMode = RFID2Key_resetSettings + 1;
    //public static final int RFID2Key_updateSettings = RFID2Key_setMode + 1;

    // region security 2
    public static final int setAdbDebugging_Enable = RFID2Key_setMode + 1;
    public static final int setAdbDebugging_Disable = setAdbDebugging_Enable + 1;
    // endregion

    // region wlan 19
    public static final int setWifiProfile = setAdbDebugging_Disable + 1;
    public static final int getWifiProfile = setWifiProfile + 1;
    public static final int setIpAssignmentProfile = getWifiProfile + 1;
    public static final int getIpAssignmentProfile = setIpAssignmentProfile + 1;
    public static final int setProxyProfile = getIpAssignmentProfile + 1;
    public static final int getProxyProfile = setProxyProfile + 1;
    public static final int deleteProfile = getProxyProfile + 1;
    public static final int isProfileExisted = deleteProfile + 1;
    public static final int connectProfile = isProfileExisted + 1;
    public static final int getConnectedProfile = connectProfile + 1;
    public static final int removeAllProfiles = getConnectedProfile + 1;
    public static final int isProfilesEmpty = removeAllProfiles + 1;  //100
    public static final int SW_Wifi_Enable = isProfilesEmpty + 1;
    public static final int SW_Wifi_Disable = SW_Wifi_Enable + 1;
    public static final int setWifiEnterpriseProfile = SW_Wifi_Disable + 1;
    public static final int setCaptivePortalMode = setWifiEnterpriseProfile + 1;
    public static final int setCaptivePortalUseHttps = setCaptivePortalMode + 1;
    public static final int setCaptivePortalHttpUrl = setCaptivePortalUseHttps + 1;
    public static final int setCaptivePortalHttpsUrl = setCaptivePortalHttpUrl + 1;
    public static final int setWifiRandomizedMac = setCaptivePortalHttpsUrl + 1;
    public static final int getWifiRandomizedMacStatus = setWifiRandomizedMac + 1;
    // endregion

    // region wlanadvanced 3
    public static final int importWlanAdvancedSettings = getWifiRandomizedMacStatus + 1;
    public static final int exportWlanAdvancedSettings = importWlanAdvancedSettings + 1;
    public static final int setNewWlanAdvancedSettings = exportWlanAdvancedSettings + 1;

    // endregion

    // region USU
    public static final int getPairingBarcode=setNewWlanAdvancedSettings+1;
    public static final int getTargetScanner=getPairingBarcode+1;
    public static final int askScannerToUnpair=getTargetScanner+1;
    public static final int getScannerSerialNumber=askScannerToUnpair+1;
    public static final int getScannerBluetoothName=getScannerSerialNumber+1;
    public static final int getScannerBluetoothMacAddress=getScannerBluetoothName+1;
    public static final int getScannerFirmwareVersion=getScannerBluetoothMacAddress+1;
    public static final int getScannerBatteryLevel=getScannerFirmwareVersion+1;
    public static final int getScannerTriggerKey=getScannerBatteryLevel+1;
    public static final int setScannerTriggerKey=getScannerTriggerKey+1;
    public static final int startDecode=setScannerTriggerKey+1;
    public static final int stopDecode=startDecode+1;
    public static final int getDataACK=stopDecode+1;
    public static final int setDataACK=getDataACK+1;
    public static final int getAutoConnection=setDataACK+1;
    public static final int setAutoConnection=getAutoConnection+1;
    public static final int getScannerSymbologyConfiguration=setAutoConnection+1;
    public static final int setScannerSymbologyConfiguration=getScannerSymbologyConfiguration+1;
    public static final int getScannerBtSignalCheckingLevel=setScannerSymbologyConfiguration+1;
    public static final int setScannerBtSignalCheckingLevel=getScannerBtSignalCheckingLevel+1;
    public static final int getScannerDataTerminator=setScannerBtSignalCheckingLevel+1;
    public static final int setScannerDataTerminator=getScannerDataTerminator+1;
    public static final int enterIntoSsiMode=setScannerDataTerminator+1;
    public static final int enterIntoRawMode=enterIntoSsiMode+1;
    public static final int receiveCurrentDataMode=enterIntoRawMode+1;
    public static final int sendACKIndicator=receiveCurrentDataMode+1;
    public static final int exportSettings=sendACKIndicator+1;
    public static final int importSettings=exportSettings+1;
    public static final int uploadSettings=importSettings+1;
    // endregion USU
//endregion

    static final String TAG = "UnitechSDK_Tester";
    static final String RESULT = "Result";
    public static final String BUNDLE_ERROR_CODE = "errorCode";
    public static final String BUNDLE_ERROR_MSG = "errorMsg";
    public static final int RESULT_CODE_ERROR = 1;
    public static final int RESULT_CODE_SUCCESS = 0;

    // Used in getAbsolutePath().
    private static String SDCARD_PATH = "/storage/sdcard1";
    private static String USB_PATH = "/storage/otgusb";
    private static String FLASH_STORAGE = "/storage/FlashStorage";

    // For function result
    private Bundle bundle = null;

    private static Context Ctx;
    public static TextView textview;
    Spinner spinnerCommand;
    private int last_index = -1;

    Spinner spinnerCat, spinnerItem;
    private int selectedCat_index = -1;
    private int selectedItem_index = -1;
    public static EditText text1Multiline;
    public static EditText text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;
    public static EditText text11, text12, text13, text14, text15, text16, text17, text18, text19, text20;
    public static TextView label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;
    public static TextView label11, label12, label13, label14, label15, label16, label17, label18, label19, label20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_scan);

        text1 = findViewById(R.id.text1);
        text1Multiline = findViewById(R.id.text1Multiline);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);
        text10 = findViewById(R.id.text10);
        text11 = findViewById(R.id.text11);
        text12 = findViewById(R.id.text12);
        text13 = findViewById(R.id.text13);
        text14 = findViewById(R.id.text14);
        text15 = findViewById(R.id.text15);
        text16 = findViewById(R.id.text16);
        text17 = findViewById(R.id.text17);
        text18 = findViewById(R.id.text18);
        text19 = findViewById(R.id.text19);
        text20 = findViewById(R.id.text20);
        label1 = findViewById(R.id.label1);
        label2 = findViewById(R.id.label2);
        label3 = findViewById(R.id.label3);
        label4 = findViewById(R.id.label4);
        label5 = findViewById(R.id.label5);
        label6 = findViewById(R.id.label6);
        label7 = findViewById(R.id.label7);
        label8 = findViewById(R.id.label8);
        label9 = findViewById(R.id.label9);
        label10 = findViewById(R.id.label10);
        label11 = findViewById(R.id.label11);
        label12 = findViewById(R.id.label12);
        label13 = findViewById(R.id.label13);
        label14 = findViewById(R.id.label14);
        label15 = findViewById(R.id.label15);
        label16 = findViewById(R.id.label16);
        label17 = findViewById(R.id.label17);
        label18 = findViewById(R.id.label18);
        label19 = findViewById(R.id.label19);
        label20 = findViewById(R.id.label20);

        textview = findViewById(R.id.textView1);

        spinnerCat = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter);
        spinnerCat.setOnItemSelectedListener(new SpinnerCatSelectedListener());

        Ctx = this.getApplicationContext();

        isStoragePermissionGranted();

        Button button_go = findViewById(R.id.button_go);
        button_go.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

            RunCmd runcmd = new RunCmd(Ctx);
            runcmd.start();

        } // End of onClick(View view)
        }); // End of button_go.setOnClickListener(new View.OnClickListener()
    }

    public class HW_BUNDLE {
        public static final String RESULT = "Result";
        public static final String STATUS = "Status";
    }


    public class RunCmd extends Thread{
        //region definition
        int ErrCode  = 0;
        String ErrMsg = "";
        Bundle bundle = new Bundle();

        boolean HW_Result = false;
        int HW_Status = 0;

        ApnCtrl apnCtrl;
        AppManagementCtrl appManagementCtrl;
        AudioCtrl audioCtrl;
        ClockCtrl clockCtrl;
        DebuggingCtrl debuggingCtrl;
        DisplayCtrl displayCtrl;
        DmiCtrl dmictrl;
        FileCtrl fileCtrl;
        FotaCtrl fotaCtrl;
        GeneralCtrl generalCtrl;
        KeymappingCtrl keymappingCtrl;
        LocationCtrl locationCtrl;
        NfcCtrl nfcCtrl;
        PowerCtrl powerCtrl;
        SafeModeLockCtrl safeModeLockCtrl;
        ScannerCtrl scannerCtrl;
        ElauncherCtrl elauncherCtrl;
        MoboLinkCtrl moboLinkCtrl;
        SoftwareUpdateCtrl softwareUpdateCtrl;
        StageGoCtrl stageGoCtrl;
        RFID2KeyCtrl rfid2KeyCtrl;
        SecurityCtrl securityCtrl;
        WlanCtrl wlanCtrl;
        WlanAdvancedCtrl wlanAdvancedCtrl;
        USUCtrl usuCtrl;

        private Context mContext;
        Handler mainHandler;

        String StrTmp = "", StrTmp2 = "", StrTmp3 = "", StrTmp4 = "",  StrTmp5 = "";
        String StrTmp6 = "", StrTmp7 = "", StrTmp8 = "";
        String ResultTmp = "";
        int intTmp = 0, intTmp2 = 0;
        boolean isWaitingHandler;
        // endregion

        public RunCmd(Context ctx){
            mContext = ctx;
            mainHandler = new Handler(mContext.getMainLooper());
            isWaitingHandler = true;
        }

        public void run() {
            final String[] value = new String[1];
            final String[] name = new String[1];
            switch (last_index) {
                case getPairingBarcode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetPairingBarcode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getTargetScanner:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetTargetScanner();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case askScannerToUnpair:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.AskScannerToUnpair();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerSerialNumber:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerSerialNumber();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerBluetoothName:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerBluetoothName();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerBluetoothMacAddress:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerBluetoothMacAddress();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerFirmwareVersion:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerFirmwareVersion();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerBatteryLevel:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerBatteryLevel();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case getScannerTriggerKey:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);
                    usuCtrl.GetScannerTriggerKey();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                    break;
                case setScannerTriggerKey:
                {
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(), "", mContext);

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (value[0]) {
                        case "0":
                            usuCtrl.SetScannerTriggerKey(false);
                            break;
                        case "1":
                            usuCtrl.SetScannerTriggerKey(true);
                            break;
                        default:
                            AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                            return;
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, printBundleContent(usuCtrl));
                }
                break;
                case startDecode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.StartDecode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case stopDecode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.StopDecode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case getDataACK:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.GetDataACK();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case setDataACK:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (value[0] )
                    {
                        case "0":
                            usuCtrl.SetDataACK(false);
                            break;
                        case "1":
                            usuCtrl.SetDataACK(true);
                            break;
                        default:
                            AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                            return;
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case getAutoConnection:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.GetAutoConnection();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case setAutoConnection:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (value[0] )
                    {
                        case "0":
                            usuCtrl.SetAutoConnection(false);
                            break;
                        case "1":
                            usuCtrl.SetAutoConnection(true);
                            break;
                        default:
                            AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                            return;
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case getScannerSymbologyConfiguration:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.GetScannerSymbologyConfiguration();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case setScannerSymbologyConfiguration:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            name[0] = text1.getText().toString();
                            value[0] = text2.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null || name[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!TextUtils.isEmpty(name[0]) && !TextUtils.isEmpty(value[0] ))
                    {
                        try
                        {
                            usuCtrl.SetScannerSymbologyConfiguration(name[0],Integer.parseInt(value[0] ));
                        }
                        catch (Exception ex)
                        {
                            Looper.prepare();
                            Toast.makeText(mContext,ex.getMessage(),Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case getScannerBtSignalCheckingLevel:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.GetScannerBtSignalCheckingLevel();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case setScannerBtSignalCheckingLevel:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (value[0])
                    {
                        case "0":
                            usuCtrl.SetScannerBtSignalCheckingLevel(0);
                            break;
                        case "1":
                            usuCtrl.SetScannerBtSignalCheckingLevel(1);
                            break;
                        default:
                            AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                            return;
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case getScannerDataTerminator:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.GetScannerDataTerminator();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case setScannerDataTerminator:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (value[0])
                    {
                        case "0":
                            usuCtrl.SetScannerDataTerminator(0);
                            break;
                        case "1":
                            usuCtrl.SetScannerDataTerminator(1);
                            break;
                        case "2":
                            usuCtrl.SetScannerDataTerminator(2);
                            break;
                        case "3":
                            usuCtrl.SetScannerDataTerminator(3);
                            break;
                        case "4":
                            usuCtrl.SetScannerDataTerminator(4);
                            break;
                        default:
                            AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                            return;
                    }
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case enterIntoSsiMode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.EnterIntoSsiMode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case enterIntoRawMode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.EnterIntoRawMode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case receiveCurrentDataMode:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.ReceiveCurrentDataMode();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case sendACKIndicator:
                {
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    final boolean[] withACK=new boolean[1],vibrate=new boolean[1];
                    final int[] beepTime=new int[1];
                    final String[] ledColor=new String[1];
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                switch (text1.getText().toString()) {
                                    case "0":
                                        withACK[0] = false;
                                        break;
                                    case "1":
                                        withACK[0] = true;
                                        break;
                                    default:
                                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid data ack");
                                        return;
                                }
                                switch (text2.getText().toString()) {
                                    case "0":
                                        beepTime[0] = 0;
                                        break;
                                    case "1":
                                        beepTime[0] = 1;
                                        break;
                                    case "2":
                                        beepTime[0] = 2;
                                        break;
                                    case "3":
                                        beepTime[0] = 3;
                                        break;
                                    default:
                                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid beep time");
                                        return;
                                }
                                switch (text3.getText().toString()) {
                                    case "0":
                                        vibrate[0] = false;
                                        break;
                                    case "1":
                                        vibrate[0] = true;
                                        break;
                                    default:
                                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid vibrate");
                                        return;
                                }
                                switch (text4.getText().toString()) {
                                    case "none":
                                    case "red":
                                    case "green":
                                    case "blue":
                                        ledColor[0] = text4.getText().toString();
                                        break;
                                    default:
                                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid led color");
                                        return;
                                }

                                //isWaitingHandler=false;
                            } catch (Exception ex) {
                                Looper.prepare();
                                Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }
                    });
                    while (ledColor[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    usuCtrl.SendACKIndicator(withACK[0],beepTime[0],vibrate[0],ledColor[0]);
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                }

                break;
                case exportSettings:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    usuCtrl.ExportSettings(value[0]);
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case importSettings:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            value[0] = text1.getText().toString();

                            //isWaitingHandler=false;
                        }
                    });
                    while (value[0]==null)
                    {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    usuCtrl.ImportSettings(value[0]);
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                case uploadSettings:
                    usuCtrl = USUCtrl.getInstance(mContext.getPackageName(),"",mContext);
                    usuCtrl.UploadSettings();
                    ErrCode = usuCtrl.getLastResultCode();
                    ErrMsg = usuCtrl.getLastResultMessage();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,printBundleContent(usuCtrl));
                    break;
                //endregion usu control

                //region Apn  control
                case ApnAddProfile:
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnAddProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnDeleteProfile://OK
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnDeleteProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnProfileIsExist://OK
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnProfileIsExist(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnUpdateProfile://OK
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnUpdateProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnDeleteAll:
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnDeleteAll();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnSetActiveProfile://OK
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnSetActiveProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case ApnGetActiveProfileApnName:
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnGetActivieProfileApnName();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);

                    ResultTmp = ", ApnName = " + bundle.getString("ApnName");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case ApnGetNameList:
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnGetNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);


                    ArrayList ALApnNameList = bundle.getStringArrayList("ApnNameList");
                    if(ALApnNameList != null)
                        ResultTmp =  "ApnNameList = "+ ALApnNameList.toString();
                    else
                        ResultTmp =  "ApnNameList = ";

                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case ApnGetProfile:
                    StrTmp = text1.getText().toString();
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.ApnGetProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);

                    for(int i=0; i<COLUMNS_OF_APN.length; i++) {
                        StrTmp = COLUMNS_OF_APN[i] + " = " +bundle.getString(COLUMNS_OF_APN[i]) +"\n";
                        if(i==0)
                            ResultTmp = StrTmp;
                        else
                            ResultTmp = ResultTmp + StrTmp;

                        Log.i(TAG,"ApnCtrl, ApnGetProfile(), COLUMNS_OF_APN[" + i + "] = " +
                                bundle.getString(COLUMNS_OF_APN[i]));
                    }

                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setAirplaneMode_Enable: //OK
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.setAirplaneMode(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setAirplaneMode_Disable: //OK
                    apnCtrl = new ApnCtrl(mContext);
                    bundle = apnCtrl.setAirplaneMode(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                //endregion Apn

                //region AppManagement  ,ea630 , runsyscmd fail.
                case installApp:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.installApp(StrTmp, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case removeApp:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.removeApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case activateApp:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.activateApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case deactivateApp:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.deactivateApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case enableApp:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.enableApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case disableApp:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.disableApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case clearAppData:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.clearAppData(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getRunningAppNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getRunningAppNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("AppNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getRunningPkgNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getRunningPkgNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("PkgNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getInstallAppNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getInstallAppNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("AppNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getInstallPkgNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getInstallPkgNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("PkgNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getBuiltinSystemAppNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getBuiltinSystemAppNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("AppNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getBuiltinSystemPkgNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getBuiltinSystemPkgNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("PkgNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getDisabledAppNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getDisabledAppNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("AppNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getDisabledPkgNameList:
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getDisabledPkgNameList();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = bundle.getStringArrayList("PkgNameList").toString();
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case osUpdate:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.osUpdate(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case runSysCmd:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.runSysCmd(StrTmp);
                    ResultTmp = bundle.getString("SysCmdResult");
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getAppInfoByAppName:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getAppInfoByAppName(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    Bundle bundle2 = bundle.getBundle("AppInfo");

                    if (bundle2 == null) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                        break;
                    }

                    Set<String> keySet = bundle2.keySet();
                    int count = 0;
                    for(String key: keySet) {
                        if(count == 0){
                            ResultTmp = key + ":" + bundle2.getString(key) + "\n";
                            count++;
                        }else{
                            ResultTmp = ResultTmp +  key + ":" + bundle2.getString(key) + "\n";
                            count++;
                        }
                    }
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case getAppInfoByPkgName:
                    StrTmp = text1.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.getAppInfoByPkgName(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    Bundle bundle3 = bundle.getBundle("AppInfo");

                    if (bundle3 == null) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                        break;
                    }

                    Set<String> keySet3 = bundle3.keySet();
                    int count3 = 0;
                    for(String key: keySet3) {
                        if(count3 == 0){
                            ResultTmp = key + ":" + bundle3.getString(key) + "\n";
                            count3++;
                        }else{
                            ResultTmp = ResultTmp +  key + ":" + bundle3.getString(key) + "\n";
                            count3++;
                        }
                    }
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg,ResultTmp);
                    break;
                case setDefaultApp:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    String[] categories = StrTmp2.split(",");
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.setDefaultApp(StrTmp,categories,StrTmp3,StrTmp4);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case clearDefaultApps_with_PackageNameArray:
                    StrTmp = text1.getText().toString();
                    String[] packages = StrTmp.split(",");
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.clearDefaultApps(packages);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case clearDefaultApps_Action_CategoriesArray:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    String[] catgories2 = StrTmp2.split(",");
                    appManagementCtrl = new AppManagementCtrl(mContext);
                    bundle = appManagementCtrl.clearDefaultApps(StrTmp, catgories2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                //endregion

                //region Audio control
//            ac.setRingtoneSound("Girtab");
//            ac.setDefaultNotificationSound("Pollux");
//            ac.setDialPadTouchTones(false);
//            ac.setTouchSound(false);
//            ac.setVibrateOnTouch(false);
                case setDefaultNotificationSound:
                    StrTmp = text1.getText().toString();
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setDefaultNotificationSound(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setRingtoneSound:
                    StrTmp = text1.getText().toString();
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setRingtoneSound(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setDialPadTouchTones_Enable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setDialPadTouchTones(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setDialPadTouchTones_Disable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setDialPadTouchTones(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setTouchSound_Enable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setTouchSound(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setTouchSound_Disable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setTouchSound(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setVibrateOnTouch_Enable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setVibrateOnTouch(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setVibrateOnTouch_Disable:
                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setVibrateOnTouch(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setVolume:
                    try {
                        intTmp = Integer.valueOf(text1.getText().toString());
                    } catch (NumberFormatException e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "invalid type");
                        break;
                    }

                    try {
                        intTmp2 = Integer.valueOf(text2.getText().toString());
                    } catch (NumberFormatException e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "invalid volume value");
                        break;
                    }

                    audioCtrl = new AudioCtrl(mContext);
                    bundle = audioCtrl.setVolume(intTmp, intTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;

                //endregion

                //region Clock  control
//            result = clockctrl.getNTPServer();
//            Log.d("MMMTTT", "ntpserver: "+ result.getString("NtpServer"));
//            result =clockctrl.getTimeMode();
//            Log.d("MMMTTT", "TimeMode: "+ result.getInt("TimeMode"));
//            result =clockctrl.getManualTime();
//            Log.d("MMMTTT", "ManualTime: "+ result.getString("ManualTime"));
//            result =clockctrl.getTimeZoneMode();
//            Log.d("MMMTTT", "TimeZoneMode: "+ result.getInt("TimeZoneMode"));
//              result =clockctrl.getTimeZone();
//              Log.d("MMMTTT", "TimeZone: "+ result.getString("TimeZone"));
//            result =clockctrl.getTimeFormat();
//            Log.d("MMMTTT", "TimeFormat: "+ result.getInt("TimeFormat"));
                // region done
                case setNTPServer:
                    StrTmp = text1.getText().toString();
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setNTPServer(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getNTPServer:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getNTPServer();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", NtpServer = " + bundle.getString("NtpServer");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setTimeMode_Enable:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeMode(1);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setTimeMode_Disable:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeMode(0);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getTimeMode:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getTimeMode();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", TimeMode = " + bundle.getInt("TimeMode");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setManualDate:
                    StrTmp = text1.getText().toString();
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setManualDate(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setManualTime:
                    StrTmp = text1.getText().toString();
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setManualTime(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getManualTime:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getManualTime();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", ManualTime = " + bundle.getString("ManualTime");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                //endregion
                case setTimeZoneMode_Enable:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeZoneMode(1);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setTimeZoneMode_Disable:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeZoneMode(0);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getTimeZoneMode:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getTimeZoneMode();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", TimeZoneMode = " + bundle.getInt("TimeZoneMode");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setTimeZone:
                    StrTmp = text1.getText().toString();
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeZone(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getTimeZone:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getTimeZone();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", TimeZone = " + bundle.getString("TimeZone");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setTimeFormat_24:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeFormat(24);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setTimeFormat_12:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.setTimeFormat(12);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getTimeFormat:
                    clockCtrl = new ClockCtrl(mContext);
                    bundle = clockCtrl.getTimeFormat();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", TimeFormat = " + bundle.getInt("TimeFormat");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                //endregion

                //region Debugging control
                case saveLogcatFile:
                    StrTmp = text1.getText().toString();
                    intTmp =  Integer.valueOf( text2.getText().toString());
                    debuggingCtrl = new  DebuggingCtrl(mContext);
                    bundle = debuggingCtrl.saveLogcatFile(StrTmp, intTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", Pid = " + bundle.getInt("Pid");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case stopSaveLogcat:
                    try {
                        intTmp = Integer.valueOf(text1.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    debuggingCtrl = new  DebuggingCtrl(mContext);
                    bundle = debuggingCtrl.stopSaveLogcat(intTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                //endregion

                // region display control
                case SetDisplayTimeout:
                    StrTmp = text1.getText().toString();
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetDisplayTimeout(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case GetDisplayTimeout:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.GetDisplayTimeout();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", DisplayTimeout = " + bundle.getString("DisplayTimeout");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case SetStayAwake_Enable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetStayAwake(1);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetStayAwake_Disable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetStayAwake(0);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case GetStayAwake:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.GetStayAwake();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", StayAwake = " + bundle.getInt("StayAwake");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case SetFontSize:
                    StrTmp = text1.getText().toString();
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetFontSize(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case GetFontSize:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.GetFontSize();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", FontSize = " + bundle.getString("FontSize");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case SetScreenBacklightLevel:
                    try {
                        intTmp = Integer.valueOf(text1.getText().toString());
                    } catch (NumberFormatException e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "invalid level");
                        break;
                    }
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenBacklightLevel(intTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case GetScreenBacklightLevel:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.GetScreenBacklightLevel();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", ScreenBacklightLevel = " + bundle.getInt("ScreenBacklightLevel");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case SetAutoBrightness_Enable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetAutoBrightness(1);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetAutoBrightness_Disable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetAutoBrightness(0);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case DisableScreenLock:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.DisableScreenLock();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetAutoRotation_Enable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetAutoRotation(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetAutoRotation_Disable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetAutoRotation(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setDisplayBatteryPercentage_Enable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.setDisplayBatteryPercentage(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setDisplayBatteryPercentage_Disable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.setDisplayBatteryPercentage(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetGloveMode_Enable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.setGloveMode(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetGloveMode_Disable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.setGloveMode(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case GetGloveMode:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.getGloveMode();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", GloveMode = " + bundle.getBoolean(DisplayCtrl.BUNDLE_GLOVE_MODE);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case ShowLockScreenNotification:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.ShowLockScreenNotification(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case HideLockScreenNotification:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.ShowLockScreenNotification(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationDisable:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_DISABLE);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationPortrait:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_PORTRAIT);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationLandscape:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_LANDSCAPE);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationReversePortrait:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_REVERSE_PORTRAIT);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationReverseLandscape:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_REVERSE_LANDSCAPE);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SetScreenOrientationAuto:
                    displayCtrl = new DisplayCtrl(mContext);
                    bundle = displayCtrl.SetScreenOrientation(DisplayCtrl.SCREEN_ROTATION_AUTO);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                //region Dmi  control
                //region Flash
                case HW_Flash_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Flash", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Flash_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Flash", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Flash_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Flash");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Camera
                case HW_Camera_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Camera", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Camera_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Camera", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Camera_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Camera");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Keyboard
                case HW_Keyboard_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Keyboard", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Keyboard_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Keyboard", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Keyboard_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Keyboard");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Usb
                case HW_Usb_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Usb", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Usb_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Usb", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Usb_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Usb");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Usb_MTP_Mode
                case HW_Usb_MTP_Mode_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Usb_MTP_Mode", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Usb_MTP_Mode_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Usb_MTP_Mode", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Usb_MTP_Mode_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Usb_MTP_Mode");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Gps
                case HW_Gps_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Gps", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Gps_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Gps", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Gps_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Gps");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region WWAN
                case HW_WWAN_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("WWAN", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_WWAN_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("WWAN", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_WWAN_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("WWAN");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Scanner
                case HW_Scanner_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Scanner", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Scanner_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Scanner", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Scanner_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Scanner");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Ime
                case HW_Ime_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Ime", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Ime_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Ime", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Ime_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Ime");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region WLAN
                case HW_WLAN_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("WLAN", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_WLAN_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("WLAN", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_WLAN_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("WLAN");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Bluetooth
                case HW_Bluetooth_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Bluetooth", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Bluetooth_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Bluetooth", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Bluetooth_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Bluetooth");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                //region Touch
                case HW_Touch_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Touch", 1);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Touch_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set("Touch", 0);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_Touch_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get("Touch");
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_DozeMode_Enable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set(DmiCtrl.DMI_DOZE_MODE, DmiCtrl.ENABLE);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_DozeMode_Disable:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Set(DmiCtrl.DMI_DOZE_MODE, DmiCtrl.DISABLE);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                case HW_DozeMode_Get_Status:
                    dmictrl = new DmiCtrl(mContext);
                    bundle = dmictrl.DCMO_Get(DmiCtrl.DMI_DOZE_MODE);
                    HW_Result = bundle.getBoolean(HW_BUNDLE.RESULT);
                    HW_Status = bundle.getInt(HW_BUNDLE.STATUS);
                    HardwareResultDisplaying(text1, text1Multiline, text2, text3, HW_Result, HW_Status);
                    break;
                //endregion

                // endregion dmi

                // region file control
                case writeUTF8ToFile:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.writeUTF8ToFile(StrTmp, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case writeToFile:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.writeToFile(StrTmp, StrTmp2.getBytes());
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case readFromFile:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.readFromFile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    if(ErrCode == RESULT_CODE_SUCCESS){
                        byte[] Result = bundle.getByteArray("Data");
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, new String(Result));
                    }else {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    }

                    break;
                case copyFile:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.copyFile(StrTmp, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case createFile:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.createFile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case deleteFile:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.deleteFile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case createFileStream:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    Boolean keepContents;
                    try {
                        keepContents = getBoolean(text3.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter: keep file contents or not");
                        return;
                    }

                    fileCtrl = FileCtrl.getInstance(mContext);

                    boolean compare = true;

                    // Write data into stream and verify
                    bundle = fileCtrl.createFileStream(StrTmp, keepContents);
                    ParcelFileDescriptor pfd = bundle.getParcelable(FileCtrl.BUNDLE_PARCEL_FILE_DESCRIPTOR);
                    if (pfd != null) {
                        long fileId = bundle.getLong(FileCtrl.BUNDLE_FILE_STREAM_ID);
                        FileOutputStream fos = new ParcelFileDescriptor.AutoCloseOutputStream(pfd);

                        Log.d(TAG, String.format("Write data to %s: %s", StrTmp, StrTmp2));
                        byte[] data = StrTmp2.getBytes();
                        try {
                            fos.write(data);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fileCtrl.closeFileStream(fileId);
                    }

                    // Read data and check
                    /**
                     * Set keepContents as true to keep file contents then you can read the file.
                     */
                    bundle = fileCtrl.createFileStream(StrTmp, true);
                    pfd = bundle.getParcelable(FileCtrl.BUNDLE_PARCEL_FILE_DESCRIPTOR);
                    if (pfd != null) {
                        long fileId = bundle.getLong(FileCtrl.BUNDLE_FILE_STREAM_ID);
                        FileInputStream fis = new ParcelFileDescriptor.AutoCloseInputStream(pfd);

                        byte[] data = StrTmp2.getBytes();
                        byte[] verify = new byte[1024];
                        ByteBuffer byteBuffer = ByteBuffer.allocate((int) pfd.getStatSize());
                        try {
                            int len = 0;
                            while ((len = fis.read(verify, 0, verify.length)) > 0) {
                                byteBuffer.put(verify, 0, len);
                            }

                            Log.d(TAG, String.format("Read from stream: %s", new String(byteBuffer.array())));
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        fileCtrl.closeFileStream(fileId);

                        if (!keepContents) {
                            verify = byteBuffer.array();
                            for (int i = 0; i < verify.length; i++) {
                                if (data[i] != verify[i]) {
                                    compare = false;
                                    Log.w(TAG, String.format("Data inconsistent at %d", i));
                                    break;
                                }
                            }
                        } else {
                            String filedata = new String(byteBuffer.array());
                            if (!filedata.endsWith(StrTmp2)) {
                                compare = false;
                            }
                        }
                    }

                    if (!compare) {
                        ErrCode = FileCtrl.RESULT_CODE_ERROR;
                        ErrMsg = "Data inconsistent";
                    } else {
                        ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                        ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    }
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case exists:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.exists(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case isDirectory:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.isDirectory(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case list:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.list(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    if(ErrCode == RESULT_CODE_SUCCESS){
                        String[] Result = bundle.getStringArray("List");
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, Result);
                    }else {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    }
                    break;
                case openFile:
                    StrTmp  = text1.getText().toString();

                    //This function cannot return result when the file doesn't exist.
                    // Reset result displaying.
                    AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "file not found!");

                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.openFile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case renameTo:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    StrTmp2 = text2.getText().toString();
                    bundle = fileCtrl.renameTo(StrTmp, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case canWrite:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.canWrite(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case canRead:
                    StrTmp  = text1.getText().toString();
                    fileCtrl = FileCtrl.getInstance(mContext);
                    bundle = fileCtrl.canRead(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region fota control
                case silentOSUpdate:
                    StrTmp = text1.getText().toString();
                    fotaCtrl = new FotaCtrl(mContext);
                    bundle = fotaCtrl.silentOSUpdate(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region general
                case setLanguage:
                    StrTmp = text1.getText().toString();
                    generalCtrl = new GeneralCtrl(mContext);
                    bundle = generalCtrl.setLanguage(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setImeCurrentKeyboard:
                    StrTmp = text1Multiline.getText().toString();
                    generalCtrl = new GeneralCtrl(mContext);
                    bundle = generalCtrl.setImeCurrentKeyboard(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getDeviceSerialNumber:
                    generalCtrl = new GeneralCtrl(mContext);
                    bundle = generalCtrl.getDeviceSerialNumber();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    if(ErrCode == RESULT_CODE_SUCCESS){
                        String Result = bundle.getString("SerialNumber");
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, Result);
                    }else {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    }
                    break;
                case getDeviceBuildNumber:
                    generalCtrl = new GeneralCtrl(mContext);
                    bundle = generalCtrl.getDeviceBuildNumber();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    if(ErrCode == RESULT_CODE_SUCCESS){
                        String Result = bundle.getString("BuildNumber");
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, Result);
                    }else {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    }
                    break;
                // endregion

                // region keymap
                case enableKeyMapping_Enable:
                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    bundle = keymappingCtrl.enableKeyMapping(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;

                case enableKeyMapping_Disable:
                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    bundle = keymappingCtrl.enableKeyMapping(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case addKeyMappings_Keycode:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    StrTmp5 = text5.getText().toString();
                    StrTmp6 = text6.getText().toString();
                    StrTmp7 = text7.getText().toString();
                    StrTmp8 = text8.getText().toString();

                    Bundle[] DownBundleArray = tansInputDataToBundleArray(StrTmp4);
                    Bundle[] UpBundleArray   = tansInputDataToBundleArray(StrTmp6);

                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);

                    try {
                        bundle = keymappingCtrl.addKeyMappings(StrTmp, StrTmp2, getBoolean(StrTmp7),
                                StrTmp3, DownBundleArray,
                                StrTmp5, UpBundleArray,
                                null);
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case addKeyMappings_LaunchApp:
                    StrTmp  = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    StrTmp5 = text5.getText().toString();
                    StrTmp6 = text6.getText().toString();
                    StrTmp7 = text7.getText().toString();
                    StrTmp8 = text8.getText().toString();

                    Bundle[] LDownBundleArray = tansInputDataToBundleArray(StrTmp4);
                    Bundle[] LUpBundleArray   = tansInputDataToBundleArray(StrTmp6);
                    Bundle[] LSapBundleArray  = tansInputDataToBundleArray(StrTmp8);

                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    
                    try {
                        bundle = keymappingCtrl.addKeyMappings(StrTmp,StrTmp2,getBoolean(StrTmp7),
                                StrTmp3, LDownBundleArray,
                                StrTmp5, LUpBundleArray,
                                LSapBundleArray);
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);

                    break;
                case resetKeyMappings:
                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    bundle = keymappingCtrl.resetKeyMappings();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case importKeyMappings:
                    StrTmp = text1.getText().toString();
                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    bundle = keymappingCtrl.importKeyMappings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case exportKeyMappings:
                    StrTmp = text1.getText().toString();
                    keymappingCtrl = KeymappingCtrl.getInstance(mContext);
                    bundle = keymappingCtrl.exportKeyMappings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region location
                case SW_setLocationMode_Enable:
                    locationCtrl = new LocationCtrl((mContext));
                    bundle = locationCtrl.setLocationMode(1);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SW_setLocationMode_Disable:
                    locationCtrl = new LocationCtrl((mContext));
                    bundle = locationCtrl.setLocationMode(0);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region nfc control
                case SW_nfc_Enable:
                    nfcCtrl = new NfcCtrl(mContext);
                    bundle = nfcCtrl.setNfcAdapter(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    Log.d(TAG, "enable nfc, ErrCode=" + ErrCode + ", ErrMsg=" + ErrMsg);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SW_nfc_Disable:
                    nfcCtrl = new NfcCtrl(mContext);
                    bundle = nfcCtrl.setNfcAdapter(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    Log.d(TAG, "disable nfc, ErrCode=" + ErrCode + ", ErrMsg=" + ErrMsg);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                //endregion

                // region power
                case ColdBoot:
                    powerCtrl = new PowerCtrl(mContext);
                    bundle = powerCtrl.ColdBoot();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case WarmBoot:
                    powerCtrl = new PowerCtrl(mContext);
                    bundle = powerCtrl.WarmBoot();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case acquireWakeLock:
                    long timeout = Long.parseLong(text1.getText().toString());
                    powerCtrl = new PowerCtrl(mContext);
                    bundle = powerCtrl.acquireWakeLock(timeout);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case releaseWakeLock:
                    powerCtrl = new PowerCtrl(mContext);
                    bundle = powerCtrl.releaseWakeLock();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region safemodelock
                case setSafeModeLock:
                    boolean enable;
                    try {
                        enable = getBoolean(text1.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid enable: " + text1.getText().toString() );
                        break;
                    }
                    StrTmp2 = text2.getText().toString();
                    safeModeLockCtrl = SafeModeLockCtrl.getInstance(mContext);
                    bundle = safeModeLockCtrl.setSafeModeLock(enable, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region scanner, pa760, ea630 ok ; ea500plus import export ,show msg ok, fail
                case importScannerSettings:
                    StrTmp = text1.getText().toString();
                    scannerCtrl = new ScannerCtrl((mContext));
                    bundle = scannerCtrl.importSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case exportScannerSettings:
                    StrTmp = text1.getText().toString();
                    scannerCtrl = new ScannerCtrl((mContext));
                    bundle = scannerCtrl.exportSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case importEaSeriesScannerSettings:
                    StrTmp = text1.getText().toString();
                    scannerCtrl = new ScannerCtrl((mContext));
                    bundle = scannerCtrl.importEaSeriesSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case exportEaSeriesScannerSettings:
                    StrTmp = text1.getText().toString();
                    scannerCtrl = new ScannerCtrl((mContext));
                    bundle = scannerCtrl.exportEaSeriesSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region uapps
                case Elauncher_setEnabled:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    try {
                        enable = getBoolean(StrTmp);
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid enable: " + StrTmp);
                        break;
                    }

                    bundle = elauncherCtrl.setEnabled(enable);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case Elauncher_setKioskApp:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    bundle = elauncherCtrl.setKioskApp(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case Elauncher_setLauncherAppList:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    bundle = elauncherCtrl.setLauncherAppList(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case Elauncher_setWallpaper:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    bundle = elauncherCtrl.setWallpaper(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case Elauncher_setPINCode:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    bundle = elauncherCtrl.setPINCode(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case Elauncher_setMode:
                    StrTmp = text1.getText().toString();
                    elauncherCtrl = new ElauncherCtrl(mContext);
                    try {
                        bundle = elauncherCtrl.setMode(Integer.valueOf(StrTmp));
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "invalid mode");
                        break;
                    }
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetServerURL:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);
                    bundle = moboLinkCtrl.SetServerURL(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetRebootstrap:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetRebootstrap(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetShowUI:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetShowUI(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetConnectionNotification:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetConnectionNotification(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetEventNotification:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetEventNotification(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetUserConfigurable:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetUserConfigurable(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetAutoRun:
                    StrTmp = text1.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetAutoRun(getBoolean(StrTmp) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case MoboLink_SetMoboLinkConfig:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    StrTmp5 = text5.getText().toString();
                    StrTmp6 = text6.getText().toString();
                    StrTmp7 = text7.getText().toString();
                    StrTmp8 = text8.getText().toString();
                    moboLinkCtrl = new MoboLinkCtrl(mContext);

                    try {
                        bundle = moboLinkCtrl.SetMoboLinkConfig(StrTmp, getBoolean(StrTmp2), getBoolean(StrTmp3), getBoolean(StrTmp4),
                                getBoolean(StrTmp5), getBoolean(StrTmp6), getBoolean(StrTmp7) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SoftwareUpdate_updateConfig:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    StrTmp5 = text5.getText().toString();
                    StrTmp6 = text6.getText().toString();
                    softwareUpdateCtrl = new SoftwareUpdateCtrl(mContext);

                    try {
                        bundle = softwareUpdateCtrl.updateConfig(StrTmp, Integer.valueOf(StrTmp2), Integer.valueOf(StrTmp3),
                                StrTmp4, Integer.valueOf(StrTmp5), StrTmp6 );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameter");
                        return;
                    }

                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SoftwareUpdate_exportSettings:
                    StrTmp = text1.getText().toString();
                    softwareUpdateCtrl = new SoftwareUpdateCtrl(mContext);
                    bundle = softwareUpdateCtrl.exportSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SoftwareUpdate_importSettings:
                    StrTmp = text1.getText().toString();
                    softwareUpdateCtrl = new SoftwareUpdateCtrl(mContext);
                    bundle = softwareUpdateCtrl.importSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SoftwareUpdate_resetSettings:
                    softwareUpdateCtrl = new SoftwareUpdateCtrl(mContext);
                    bundle = softwareUpdateCtrl.resetSettings();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_setReportPath:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.setReportPath(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_setReportMaxNum:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.setReportMaxNum(Integer.valueOf(StrTmp));
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_enabledBootupScript:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.enabledBootupScript(getBoolean(StrTmp));
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_setBootupScript:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.setBootupScript(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_setPasscode:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.setPasscode(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case StageGo_setScanMode:
                    StrTmp = text1.getText().toString();
                    stageGoCtrl = new StageGoCtrl(mContext);
                    bundle = stageGoCtrl.setScanMode(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case RFID2Key_importSettings:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    rfid2KeyCtrl = new RFID2KeyCtrl(mContext);
                    bundle = rfid2KeyCtrl.importSettings(StrTmp, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case RFID2Key_exportSettings:
                    StrTmp = text1.getText().toString();
                    rfid2KeyCtrl = new RFID2KeyCtrl(mContext);
                    bundle = rfid2KeyCtrl.exportSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case RFID2Key_resetSettings:
                    StrTmp = text1.getText().toString();
                    rfid2KeyCtrl = new RFID2KeyCtrl(mContext);
                    bundle = rfid2KeyCtrl.resetSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case RFID2Key_setMode:
                    Boolean rfidMode = getBoolean(text1.getText().toString());
                    StrTmp2 = text2.getText().toString();
                    rfid2KeyCtrl = new RFID2KeyCtrl(mContext);
                    bundle = rfid2KeyCtrl.setMode(rfidMode, StrTmp2);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
//                case RFID2Key_updateSettings:
//                    rfid2KeyCtrl = new RFID2KeyCtrl(mContext);
//                    Boolean rfid2key = getBoolean(text1.getText().toString());
//                    Boolean quickSwitch = getBoolean(text2.getText().toString());
//                    int quickSwitchTime = Integer.valueOf(text3.getText().toString());
//                    Boolean readerOnce = getBoolean(text4.getText().toString());
//                    String passcode = text5.getText().toString();
//                    Boolean autoStart = getBoolean(text6.getText().toString());
//                    String region = text7.getText().toString();
//                    int readPower = Integer.valueOf(text8.getText().toString());
//                    int readerTimeout = Integer.valueOf(text9.getText().toString());
//                    String encoding = text10.getText().toString();
//                    String session = text11.getText().toString();
//                    String target  = text12.getText().toString();
//                    String q  = text13.getText().toString();
//                    int format = Integer.valueOf(text14.getText().toString());
//                    int outputMethod = Integer.valueOf(text15.getText().toString());
//                    int outputDuration  = Integer.valueOf(text16.getText().toString());
//                    Boolean beep = getBoolean(text17.getText().toString());
//                    String prefix  = text18.getText().toString();
//                    String suffix = text19.getText().toString();
//                    int terminator = Integer.valueOf(text20.getText().toString());
//                    bundle = rfid2KeyCtrl.updateSettings(rfid2key, quickSwitch, quickSwitchTime, readerOnce, passcode,
//                                                         autoStart, region, readPower, readerTimeout, encoding,
//                                                         session, target, q, format, outputMethod,
//                                                         outputDuration, beep, prefix, suffix, terminator);
//                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
//                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
//
//                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
//                    break;

                // endregion

                // region security
                case setAdbDebugging_Enable:
                    securityCtrl = new SecurityCtrl(mContext);
                    bundle = securityCtrl.setAdbDebugging(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setAdbDebugging_Disable:
                    securityCtrl = new SecurityCtrl(mContext);
                    bundle = securityCtrl.setAdbDebugging(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion

                // region wlan, setProxyProfile ea630 fail.
                case setWifiProfile:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    intTmp  =   Integer.valueOf( text3.getText().toString());
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setWifiProfile(StrTmp, StrTmp2, intTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getWifiProfile:
                    StrTmp = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.getWifiProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", SecurityMode = " + bundle.getInt("SecurityMode");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setIpAssignmentProfile:
                    StrTmp  = text1.getText().toString();
                    intTmp  = Integer.valueOf( text2.getText().toString());
                    StrTmp2 = text3.getText().toString();
                    intTmp2 = Integer.valueOf( text4.getText().toString());
                    StrTmp3 = text5.getText().toString();
                    StrTmp4 = text6.getText().toString();
                    StrTmp5 = text7.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setIpAssignmentProfile(StrTmp, intTmp, StrTmp2, intTmp2, StrTmp3, StrTmp4, StrTmp5);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getIpAssignmentProfile:
                    StrTmp  = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.getIpAssignmentProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    int IpAssignment    = bundle.getInt("IpAssignment");
                    String IpAddress    = bundle.getString("IpAddress");
                    int PrefixLength    = bundle.getInt("PrefixLength");
                    String Gateway      = bundle.getString("Gateway");
                    String Dns1         = bundle.getString("Dns1");
                    String Dns2         = bundle.getString("Dns2");
                    ResultTmp = ", SSID = "           + StrTmp + "\n" +
                                ", IpAssignment = "   + IpAssignment + "\n" +
                                ", IpAddress = "      + IpAddress + "\n" +
                                ", PrefixLength = "   + PrefixLength + "\n" +
                                ", Gateway = "        + Gateway + "\n" +
                                ", Dns1 = "           + Dns1 + "\n" +
                                ", Dns2 = "           + Dns2;
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case setProxyProfile:                    StrTmp  = text1.getText().toString();
                    intTmp  = Integer.valueOf( text2.getText().toString());
                    StrTmp2 = text3.getText().toString();
                    StrTmp3 = text4.getText().toString();
                    intTmp2 = Integer.valueOf( text5.getText().toString());
                    StrTmp4 = text6.getText().toString();

                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setProxyProfile(StrTmp, intTmp, StrTmp2, StrTmp3, intTmp2, StrTmp4);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getProxyProfile:
                    StrTmp  = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.getProxyProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);

                    int ProxySettings   = bundle.getInt("ProxySettings");
                    String PacFileUrl   = bundle.getString("PacFileUrl");
                    String Host         = bundle.getString("Host");
                    int Port            = bundle.getInt("Port");
                    String ExcList      = bundle.getString("ExcList");
                    ResultTmp = ", SSID = "       + StrTmp + "\n" +
                            ", ProxySettings = "  + ProxySettings + "\n" +
                            ", PacFileUrl = "     + PacFileUrl + "\n" +
                            ", Host = "           + Host + "\n" +
                            ", Port = "           + Port + "\n" +
                            ", ExcList = "        + ExcList;
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case deleteProfile:
                    StrTmp = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.deleteProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case isProfileExisted:
                    StrTmp = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.isProfileExisted(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case connectProfile:
                    StrTmp = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.connectProfile(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getConnectedProfile:
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.getConnectedProfile();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    ResultTmp = ", ssid = " + bundle.getString("ssid");
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    break;
                case removeAllProfiles:
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.removeAllProfiles();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case isProfilesEmpty:
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.isProfilesEmpty();
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SW_Wifi_Enable:
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setWifiAdapter(true);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case SW_Wifi_Disable:
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setWifiAdapter(false);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setWifiEnterpriseProfile:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    StrTmp3 = text3.getText().toString();
                    StrTmp4 = text4.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setWifiEnterpriseProfile(StrTmp, StrTmp2, StrTmp3, StrTmp4);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setCaptivePortalMode:
                    int mode;
                    try {
                        mode = Integer.parseInt(text1.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setCaptivePortalMode(mode);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setCaptivePortalUseHttps:
                    try{
                        mode = Integer.parseInt(text1.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setCaptivePortalUseHttps(mode);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setCaptivePortalHttpUrl:
                    String url = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setCaptivePortalHttpUrl(url);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setCaptivePortalHttpsUrl:
                    url = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.setCaptivePortalHttpsUrl(url);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setWifiRandomizedMac:
                    StrTmp = text1.getText().toString();
                    StrTmp2 = text2.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    try {
                        bundle = wlanCtrl.setWifiRandomizedMac(StrTmp, getBoolean(StrTmp2) );
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case getWifiRandomizedMacStatus:
                    StrTmp = text1.getText().toString();
                    wlanCtrl = new WlanCtrl(mContext);
                    bundle = wlanCtrl.getWifiRandomizedMacStatus(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    try {
                        ResultTmp = ", Enabled = " + bundle.getBoolean(WlanCtrl.BUNDLE_ENABLED);
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg, ResultTmp);
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    }
                    break;
                // endregion

                // region wlanadvanced
                case importWlanAdvancedSettings:
                    StrTmp = text1.getText().toString();
                    wlanAdvancedCtrl = new WlanAdvancedCtrl(mContext);
                    bundle = wlanAdvancedCtrl.importSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case exportWlanAdvancedSettings:
                    StrTmp = text1.getText().toString();
                    wlanAdvancedCtrl = new WlanAdvancedCtrl(mContext);
                    bundle = wlanAdvancedCtrl.exportSettings(StrTmp);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                case setNewWlanAdvancedSettings:
                    int powerSaveMode, roamingTrigger, roamingDelta, roamingScanPeriod;
                    try {
                        powerSaveMode = Integer.valueOf(text1.getText().toString());
                        roamingTrigger = Integer.valueOf(text2.getText().toString());
                        roamingDelta = Integer.valueOf(text3.getText().toString());
                        roamingScanPeriod = Integer.valueOf(text4.getText().toString());
                    } catch (Exception e) {
                        AppResultDisplaying(text1, text1Multiline, text2, text3, RESULT_CODE_ERROR, "Invalid parameters");
                        break;
                    }
                    wlanAdvancedCtrl = new WlanAdvancedCtrl(mContext);
                    bundle = wlanAdvancedCtrl.setNewSetting(powerSaveMode, roamingTrigger, roamingDelta, roamingScanPeriod);
                    ErrCode = bundle.getInt(BUNDLE_ERROR_CODE);
                    ErrMsg = bundle.getString(BUNDLE_ERROR_MSG);
                    AppResultDisplaying(text1, text1Multiline, text2, text3, ErrCode, ErrMsg);
                    break;
                // endregion
            }
        } // end of run

        private String printBundleContent(USUCtrl usuCtrl) {
            if(usuCtrl.replyQueue.isEmpty())
                return "";
            else
            {
                Bundle extras=usuCtrl.replyQueue.poll();
                StringBuilder builder = new StringBuilder("Extras:\n");
                if(extras!=null)
                {
                    for (String key : extras.keySet()) { //extras is the Bundle containing info
                        Object value = extras.get(key); //get the current object
                        builder.append(key).append(": ").append(value).append("\n"); //add the key-value pair to the
                    }
                    return builder.toString();
                }
                else
                {
                    return "";
                }

            }
        }
    } // end of


//region Show Result
    private class STATUS {
        public static final int DISABLE = 0;
        public static final int ENABLE = 1;
        public static final int NOT_SUPPORT = 2;
    }


    /*
     result = true  → Execution Success
              false → Execution Fail
     Status = 0     → Disable
              1     → Enable
              2     → Not support

     */
    private void HardwareResultDisplaying(final EditText text1,
                                          final EditText text1Multiline,
                                          final EditText text2,
                                          final EditText text3,
                                          final boolean result,
                                          final int status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String ShowStatusMsg = ", Status = ";
                if (status == STATUS.DISABLE)
                    ShowStatusMsg = ShowStatusMsg + " (DISABLE)";
                else if (status == STATUS.ENABLE)
                    ShowStatusMsg = ShowStatusMsg + " (ENABLE)";
                else
                    ShowStatusMsg = ShowStatusMsg + " (NOT_SUPPORT)";

                if (result) {
                    textview.setText("Execution Success" + ShowStatusMsg);
                } else {
                    textview.setText("Execution Fail" + ShowStatusMsg);
                }
            }
        });
    }

    private void AppResultDisplaying(final EditText text1,
                                     final EditText text1Multiline,
                                     final EditText text2,
                                     final EditText text3,
                                     final int errCode,
                                     final String errMsg) {

        AppResultDisplaying(text1, text1Multiline, text2, text3, errCode, errMsg, "");
    }

    private void AppResultDisplaying(final EditText text1,
                                     final EditText text1Multiline,
                                     final EditText text2,
                                     final EditText text3,
                                     final int errCode,
                                     final String errMsg,
                                     final String resultMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String ShowMsgData = ", ErrMsg=" + errMsg;

                if(!resultMsg.equals(""))
                    ShowMsgData = ShowMsgData + "\n" + resultMsg;

                if (errCode == RESULT_CODE_SUCCESS)
                    textview.setText("Execution result Success"+ ShowMsgData);
                else
                    textview.setText("Execution result Fail" + ShowMsgData);
            }
        });
    }

    private void AppResultDisplaying(final EditText text1,
                                     final EditText text1Multiline,
                                     final EditText text2,
                                     final EditText text3,
                                     final int errCode,
                                     final String errMsg,
                                     final String[] resultMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String ShowMsgData = ", ErrMsg=" + errMsg;

                if(resultMsg != null) {
                    for (String tempString : resultMsg) {
                        ShowMsgData = ShowMsgData + "\n" + tempString;
                    }
                }

                if (errCode == RESULT_CODE_SUCCESS)
                    textview.setText("Execution result Success"+ ShowMsgData);
                else
                    textview.setText("Execution result Fail" + ShowMsgData);
            }
        });
    }

//endregion


    private class SpinnerCatSelectedListener implements
	            AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Button button_go = findViewById(R.id.button_go);
            TextView label1 = findViewById(R.id.label1);
            EditText text1 = findViewById(R.id.text1);
            EditText text1Multiline = findViewById(R.id.text1Multiline);
            TextView label2 = findViewById(R.id.label2);
            EditText text2 = findViewById(R.id.text2);
            TextView label3 = findViewById(R.id.label3);
            EditText text3 = findViewById(R.id.text3);


            if (selectedCat_index == -1 || position > 0) {

                // region Init Screen

                button_go.setEnabled(true);

                CleanScreen(label1,text1,text1Multiline,label2,text2,label3,text3);

                selectedCat_index = position;

                //region Set Spinner Item list data
                spinnerItem = findViewById(R.id.spinner2);

                ArrayAdapter<CharSequence> adapter = null;
                switch (position) {
                    case cmd.apn:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.apn, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.appmanagement:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.appmanagement, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.audio:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.audio, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.clock:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.clock, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.debugging:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.debugging, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.display:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.display, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.dmi:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.dmi, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.file:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.file, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.fota:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.fota, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.general:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.general, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.keymap:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.keymap, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.location:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.location, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.nfc:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.nfc, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.power:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.power, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.safemodelock:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.safemodelock, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.scanner:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.scanner, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.uapps:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.uapps, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.security:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.security, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.wlan:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.wlan, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.wlanadvanced:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.wlanadvanced, android.R.layout.simple_spinner_item);
                        break;
                    case cmd.usu:
                        adapter = ArrayAdapter.createFromResource(Ctx,
                                R.array.usu, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        break;

                }

                if(adapter != null)
                {
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerItem.setAdapter(adapter);
                    spinnerItem.setOnItemSelectedListener(new SpinnerItemSelectedListener());
                    spinnerItem.setEnabled(true);
                }

                spinnerItem.setSelection(0);


            } else {
                // Reselect the previous item, so OnItemSelected will be called again,
                // and everything will be reinitialized for this selection

//                if(selectedCat_index == -1 || position == 0) {
//                    button_go.setEnabled(false);
//                    cleanscreen(label1, text1, text1Multiline, label2, text2, label3, text3);
//                }
//                spinnerCat.setSelection(selectedCat_index);

                CleanScreen(label1, text1, text1Multiline, label2, text2, label3, text3);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    }

    private class SpinnerItemSelectedListener implements
            AdapterView.OnItemSelectedListener {

        @SuppressLint({"SetTextI18n", "SdCardPath"})
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Button button_go = findViewById(R.id.button_go);
            TextView label1 = findViewById(R.id.label1);
            EditText text1 = findViewById(R.id.text1);
            EditText text1Multiline = findViewById(R.id.text1Multiline);
            TextView label2 = findViewById(R.id.label2);
            EditText text2 = findViewById(R.id.text2);
            TextView label3 = findViewById(R.id.label3);
            EditText text3 = findViewById(R.id.text3);
            TextView label4 = findViewById(R.id.label4);
            EditText text4 = findViewById(R.id.text4);
            TextView label5 = findViewById(R.id.label5);
            EditText text5 = findViewById(R.id.text5);
            TextView label6 = findViewById(R.id.label6);
            EditText text6 = findViewById(R.id.text6);
            TextView label7 = findViewById(R.id.label7);
            EditText text7 = findViewById(R.id.text7);
            TextView label8 = findViewById(R.id.label8);
            EditText text8 = findViewById(R.id.text8);
            TextView label9 = findViewById(R.id.label9);
            EditText text9 = findViewById(R.id.text9);
            TextView label10 = findViewById(R.id.label10);
            EditText text10 = findViewById(R.id.text10);
            TextView label11 = findViewById(R.id.label11);
            EditText text11 = findViewById(R.id.text11);
            TextView label12 = findViewById(R.id.label12);
            EditText text12 = findViewById(R.id.text12);
            TextView label13 = findViewById(R.id.label13);
            EditText text13 = findViewById(R.id.text13);
            TextView label14 = findViewById(R.id.label14);
            EditText text14 = findViewById(R.id.text14);
            TextView label15 = findViewById(R.id.label15);
            EditText text15 = findViewById(R.id.text15);
            TextView label16 = findViewById(R.id.label16);
            EditText text16 = findViewById(R.id.text16);
            TextView label17 = findViewById(R.id.label17);
            EditText text17 = findViewById(R.id.text17);
            TextView label18 = findViewById(R.id.label18);
            EditText text18 = findViewById(R.id.text18);
            TextView label19 = findViewById(R.id.label19);
            EditText text19 = findViewById(R.id.text19);
            TextView label20 = findViewById(R.id.label20);
            EditText text20 = findViewById(R.id.text20);

            if (selectedItem_index == -1 || position > 0) {
                button_go.setEnabled(true);
                CleanScreen(label1,text1,text1Multiline,label2,text2,label3,text3);
                selectedItem_index = position;

                switch(selectedCat_index){
                    //region cmd.apn
                    case cmd.apn:
                        switch(selectedItem_index){
                            case cmd.Apn.ApnAddProfile:
                                last_index = ApnAddProfile;
                                label1.setText("Apn AddProfile");
                                text1.setText("kk;46692;466;92;internet;;;;;;;;;0;default,supl;1;IP;IP;1;0;;");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.ApnDeleteProfile:
                                last_index = ApnDeleteProfile;
                                label1.setText("Apn DeleteProfile");
                                text1.setText("kk");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.ApnProfileIsExist:
                                last_index = ApnProfileIsExist;
                                label1.setText("Apn ProfileIsExist");
                                text1.setText("kk");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.ApnUpdateProfile:
                                last_index = ApnUpdateProfile;
                                label1.setText("Apn UpdateProfile");
                                text1.setText("kk;46692;466;92;internet2;;;;;;;;;0;default,supl;1;IP;IP;1;0;;");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.ApnDeleteAll:
                                last_index = ApnDeleteAll;
                                break;
                            case cmd.Apn.ApnSetActiveProfile:
                                last_index = ApnSetActiveProfile;
                                label1.setText("Apn SetActiveProfile");
                                text1.setText("kk");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.ApnGetActivieProfileApnName:
                                last_index = ApnGetActiveProfileApnName;
                                break;
                            case cmd.Apn.ApnGetNameList:
                                last_index = ApnGetNameList;
                                break;
                            case cmd.Apn.ApnGetProfile:
                                last_index = ApnGetProfile;
                                label1.setText("Apn GetProfile");
                                text1.setText("kk");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Apn.setAirplaneMode_Enable:
                                last_index = setAirplaneMode_Enable;
                                break;
                            case cmd.Apn.setAirplaneMode_Disable:
                                last_index = setAirplaneMode_Disable;
                                break;

                        }
                        break;
                    //endregion

                    // region cmd.appmanagement
                    case cmd.appmanagement:
                        switch(selectedItem_index){
                            case cmd.Appmanagement.installApp:
                                last_index = installApp;
                                label1.setText("ApkFilePath");
                                text1.setText("/sdcard/verone.apk");
                                label2.setText("PackageName");
                                text2.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.removeApp:
                                last_index = removeApp;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.activateApp:
                                last_index = activateApp;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.deactivateApp:
                                last_index = deactivateApp;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.enableApp:
                                last_index = enableApp;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.disableApp:
                                last_index = disableApp;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.clearAppData:
                                last_index = clearAppData;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.getRunningAppNameList:
                                last_index = getRunningAppNameList;
                                break;
                            case cmd.Appmanagement.getRunningPkgNameList:
                                last_index = getRunningPkgNameList;
                                break;
                            case cmd.Appmanagement.getInstallAppNameList:
                                last_index = getInstallAppNameList;
                                break;
                            case cmd.Appmanagement.getInstallPkgNameList:
                                last_index = getInstallPkgNameList;
                                break;
                            case cmd.Appmanagement.getBuiltinSystemAppNameList:
                                last_index = getBuiltinSystemAppNameList;
                                break;
                            case cmd.Appmanagement.getBuiltinSystemPkgNameList:
                                last_index = getBuiltinSystemPkgNameList;
                                break;
                            case cmd.Appmanagement.getDisabledAppNameList:
                                last_index = getDisabledAppNameList;
                                break;
                            case cmd.Appmanagement.getDisabledPkgNameList:
                                last_index = getDisabledPkgNameList;
                                break;
                            case cmd.Appmanagement.osUpdate:
                                last_index = osUpdate;
                                label1.setText("Update Zip Path");
                                text1.setText("/sdcard/pp.zip");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.runSysCmd:
                                last_index = runSysCmd;
                                label1.setText("adb shell command");
                                //text1.setText("echo 333 > /sdcard/123.txt");
                                text1.setText("dumpsys meminfo com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.getAppInfoByAppName:
                                last_index = getAppInfoByAppName;
                                label1.setText("AppName");
                                text1.setText("verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.getAppInfoByPkgName:
                                last_index = getAppInfoByPkgName;
                                label1.setText("PackageName");
                                text1.setText("com.example.verone");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.setDefaultApp:
                                last_index = setDefaultApp;
                                label1.setText("Action");
                                text1.setText("android.intent.action.MAIN");
                                label2.setText("Categories Array, (split with , )");
                                text2.setText("android.intent.category.HOME,android.intent.category.DEFAULT");
                                label3.setText("PackageName");
                                text3.setText("com.ute.eu.ELauncher");
                                label4.setText("ActivityName");
                                text4.setText("com.ute.eu.ELauncher.Launcher");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Appmanagement.clearDefaultApps_with_PackageNameArray:
                                last_index = clearDefaultApps_with_PackageNameArray;
                                label1.setText("PackageName Array, (split with , )");
                                text1.setText("com.ute.eu.ELauncher,com.unitech.unote");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);

                                break;
                            case cmd.Appmanagement.clearDefaultApps_Action_CategoriesArray:
                                last_index = clearDefaultApps_Action_CategoriesArray;
                                label1.setText("Action");
                                text1.setText("android.intent.action.MAIN");
                                label2.setText("Categories Array, (split with , )");
                                text2.setText("android.intent.category.HOME,android.intent.category.DEFAULT");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.audio
                    //            ac.setRingtoneSound("Girtab");
                    //            ac.setDefaultNotificationSound("Pollux");
                    case cmd.audio:
                        switch(selectedItem_index){
                            case cmd.Audio.setDefaultNotificationSound:
                                last_index = setDefaultNotificationSound;
                                label1.setText("Audio setDefaultNotificationSound");
                                text1.setText("Pollux");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Audio.setRingtoneSound:
                                last_index = setRingtoneSound;
                                label1.setText("Audio setRingtoneSound");
                                text1.setText("Girtab");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Audio.setDialPadTouchTones_Enable:
                                last_index = setDialPadTouchTones_Enable;
                                break;
                            case cmd.Audio.setDialPadTouchTones_Disable:
                                last_index = setDialPadTouchTones_Disable;
                                break;
                            case cmd.Audio.setTouchSound_Enable:
                                last_index = setTouchSound_Enable;
                                break;
                            case cmd.Audio.setTouchSound_Disable:
                                last_index = setTouchSound_Disable;
                                break;
                            case cmd.Audio.setVibrateOnTouch_Enable:
                                last_index = setVibrateOnTouch_Enable;
                                break;
                            case cmd.Audio.setVibrateOnTouch_Disable:
                                last_index = setVibrateOnTouch_Disable;
                                break;
                            case cmd.Audio.setVolume:
                                last_index = setVolume;
                                label1.setText("Type (2:Ring, 3:Media, 4:Alarm):");
                                text1.setText("2");
                                label2.setText("VolumeValue (0-100):");
                                text2.setText("30");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;

                        }
                        break;
                    //endregion

                    // region cmd.clock
                    case cmd.clock:
                        switch(selectedItem_index){
                            case cmd.Clock.setNTPServer:
                                last_index = setNTPServer;
                                label1.setText("Clock setNTPServer");
                                text1.setText("www.ff.cc");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.getNTPServer:
                                last_index = getNTPServer;
                                break;
                            case cmd.Clock.setTimeMode_Enable:
                                last_index = setTimeMode_Enable;
                                break;
                            case cmd.Clock.setTimeMode_Disable:
                                last_index = setTimeMode_Disable;
                                break;
                            case cmd.Clock.getTimeMode:
                                last_index = getTimeMode;
                                label1.setText("0:manual mode, 1:auto mode");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.setManualDate:
                                last_index = setManualDate;
                                label1.setText("Clock setManualDate");
                                text1.setText("31/10/2019");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.setManualTime:
                                last_index = setManualTime;
                                label1.setText("Clock setManualTime");
                                text1.setText("16:30");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.getManualTime:
                                last_index = getManualTime;
                                break;
                            case cmd.Clock.setTimeZoneMode_Enable:
                                last_index = setTimeZoneMode_Enable;
                                break;
                            case cmd.Clock.setTimeZoneMode_Disable:
                                last_index = setTimeZoneMode_Disable;
                                break;
                            case cmd.Clock.getTimeZoneMode:
                                last_index = getTimeZoneMode;
                                label1.setText("0:manual mode, 1:auto mode");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.setTimeZone:
                                last_index = setTimeZone;
                                label1.setText("Clock setTimeZone");
                                text1.setText("Andorra");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Clock.getTimeZone:
                                last_index = getTimeZone;
                                break;
                            case cmd.Clock.setTimeFormat_24:
                                last_index = setTimeFormat_24;
                                break;
                            case cmd.Clock.setTimeFormat_12:
                                last_index = setTimeFormat_12;
                                break;
                            case cmd.Clock.getTimeFormat:
                                last_index = getTimeFormat;
                                label1.setText("12 or 24");
                                label1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.debugging
                    case cmd.debugging:
                        switch(selectedItem_index){
                            case cmd.Debugging.saveLogcatFile:
                                last_index = saveLogcatFile;
                                label1.setText("path");
                                text1.setText("/sdcard/33.txt");
                                label2.setText("savetime(second)");
                                text2.setText("40");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Debugging.stopSaveLogcat:
                                last_index = stopSaveLogcat;
                                label1.setText("stop process pid");
                                text1.setText("");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.display
                    case cmd.display:
                        switch(selectedItem_index){
                            case cmd.Display.SetDisplayTimeout:
                                last_index = SetDisplayTimeout;
                                label1.setText("Timeout: (ex: NEVER, 15, 30, 60, 120, 300, 600, 1800 (second))");
                                text1.setText("30");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.GetDisplayTimeout:
                                last_index = GetDisplayTimeout;
                                label1.setText("Timeout: (ex: NEVER, 15, 30, 60, 120, 300, 600, 1800 (second))");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.SetStayAwake_Enable:
                                last_index = SetStayAwake_Enable;
                                break;
                            case cmd.Display.SetStayAwake_Disable:
                                last_index = SetStayAwake_Disable;
                                break;
                            case cmd.Display.GetStayAwake:
                                last_index = GetStayAwake;
                                label1.setText("0:false, 1:true");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.SetFontSize:
                                last_index = SetFontSize;
                                label1.setText("size: (small, default, large, largest)");
                                text1.setText("large");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.GetFontSize:
                                last_index = GetFontSize;
                                label1.setText("size: (small, default, large, largest)");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.SetScreenBacklightLevel:
                                last_index = SetScreenBacklightLevel;
                                label1.setText("Level (0-255):");
                                text1.setText("80");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.GetScreenBacklightLevel:
                                last_index = GetScreenBacklightLevel;
                                label1.setText("range: 0-255");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Display.SetAutoBrightness_Enable:
                                last_index = SetAutoBrightness_Enable;
                                break;
                            case cmd.Display.SetAutoBrightness_Disable:
                                last_index = SetAutoBrightness_Disable;
                                break;
                            case cmd.Display.DisableScreenLock:
                                last_index = DisableScreenLock;
                                break;
                            case cmd.Display.SetAutoRotation_Enable:
                                last_index = SetAutoRotation_Enable;
                                break;
                            case cmd.Display.SetAutoRotation_Disable:
                                last_index = SetAutoRotation_Disable;
                                break;
                            case cmd.Display.setDisplayBatteryPercentage_Enable:
                                last_index = setDisplayBatteryPercentage_Enable;
                                break;
                            case cmd.Display.setDisplayBatteryPercentage_Disable:
                                last_index = setDisplayBatteryPercentage_Disable;
                                break;
                            case cmd.Display.SetGloveMode_Enable:
                                last_index = SetGloveMode_Enable;
                                break;
                            case cmd.Display.SetGloveMode_Disable:
                                last_index = SetGloveMode_Disable;
                                break;
                            case cmd.Display.GetGloveMode:
                                last_index = GetGloveMode;
                                break;
                            case cmd.Display.ShowLockScreenNotification:
                                last_index = ShowLockScreenNotification;
                                break;
                            case cmd.Display.HideLockScreenNotification:
                                last_index = HideLockScreenNotification;
                                break;
                            case cmd.Display.SetScreenOrientationDisable:
                                last_index = SetScreenOrientationDisable;
                                break;
                            case cmd.Display.SetScreenOrientationPortrait:
                                last_index = SetScreenOrientationPortrait;
                                break;
                            case cmd.Display.SetScreenOrientationLandscape:
                                last_index = SetScreenOrientationLandscape;
                                break;
                            case cmd.Display.SetScreenOrientationReversePortrait:
                                last_index = SetScreenOrientationReversePortrait;
                                break;
                            case cmd.Display.SetScreenOrientationReverseLandscape:
                                last_index = SetScreenOrientationReverseLandscape;
                                break;
                            case cmd.Display.SetScreenOrientationAuto:
                                last_index = SetScreenOrientationAuto;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.dmi
                    case cmd.dmi:
                        switch(selectedItem_index){
                            case cmd.Dmi.HW_Flash_Enable:
                                last_index = HW_Flash_Enable;
                                break;
                            case cmd.Dmi.HW_Flash_Disable:
                                last_index = HW_Flash_Disable;
                                break;
                            case cmd.Dmi.HW_Flash_Get_Status:
                                last_index = HW_Flash_Get_Status;
                                break;
                            case cmd.Dmi.HW_Camera_Enable:
                                last_index = HW_Camera_Enable;
                                break;
                            case cmd.Dmi.HW_Camera_Disable:
                                last_index = HW_Camera_Disable;
                                break;
                            case cmd.Dmi.HW_Camera_Get_Status:
                                last_index = HW_Camera_Get_Status;
                                break;
                            case cmd.Dmi.HW_Keyboard_Enable:
                                last_index = HW_Keyboard_Enable;
                                break;
                            case cmd.Dmi.HW_Keyboard_Disable:
                                last_index = HW_Keyboard_Disable;
                                break;
                            case cmd.Dmi.HW_Keyboard_Get_Status:
                                last_index = HW_Keyboard_Get_Status;
                                break;
                            case cmd.Dmi.HW_Usb_Enable:
                                last_index = HW_Usb_Enable;
                                break;
                            case cmd.Dmi.HW_Usb_Disable:
                                last_index = HW_Usb_Disable;
                                break;
                            case cmd.Dmi.HW_Usb_Get_Status:
                                last_index = HW_Usb_Get_Status;
                                break;
                            case cmd.Dmi.HW_Usb_MTP_Mode_Enable:
                                last_index = HW_Usb_MTP_Mode_Enable;
                                break;
                            case cmd.Dmi.HW_Usb_MTP_Mode_Disable:
                                last_index = HW_Usb_MTP_Mode_Disable;
                                break;
                            case cmd.Dmi.HW_Usb_MTP_Mode_Get_Status:
                                last_index = HW_Usb_MTP_Mode_Get_Status;
                                break;
                            case cmd.Dmi.HW_Gps_Enable:
                                last_index = HW_Gps_Enable;
                                break;
                            case cmd.Dmi.HW_Gps_Disable:
                                last_index = HW_Gps_Disable;
                                break;
                            case cmd.Dmi.HW_Gps_Get_Status:
                                last_index = HW_Gps_Get_Status;
                                break;
                            case cmd.Dmi.HW_WWAN_Enable:
                                last_index = HW_WWAN_Enable;
                                break;
                            case cmd.Dmi.HW_WWAN_Disable:
                                last_index = HW_WWAN_Disable;
                                break;
                            case cmd.Dmi.HW_WWAN_Get_Status:
                                last_index = HW_WWAN_Get_Status;
                                break;
                            case cmd.Dmi.HW_Scanner_Enable:
                                last_index = HW_Scanner_Enable;
                                break;
                            case cmd.Dmi.HW_Scanner_Disable:
                                last_index = HW_Scanner_Disable;
                                break;
                            case cmd.Dmi.HW_Scanner_Get_Status:
                                last_index = HW_Scanner_Get_Status;
                                break;
                            case cmd.Dmi.HW_Ime_Enable:
                                last_index = HW_Ime_Enable;
                                break;
                            case cmd.Dmi.HW_Ime_Disable:
                                last_index = HW_Ime_Disable;
                                break;
                            case cmd.Dmi.HW_Ime_Get_Status:
                                last_index = HW_Ime_Get_Status;
                                break;
                            case cmd.Dmi.HW_WLAN_Enable:
                                last_index = HW_WLAN_Enable;
                                break;
                            case cmd.Dmi.HW_WLAN_Disable:
                                last_index = HW_WLAN_Disable;
                                break;
                            case cmd.Dmi.HW_WLAN_Get_Status:
                                last_index = HW_WLAN_Get_Status;
                                break;
                            case cmd.Dmi.HW_Bluetooth_Enable:
                                last_index = HW_Bluetooth_Enable;
                                break;
                            case cmd.Dmi.HW_Bluetooth_Disable:
                                last_index = HW_Bluetooth_Disable;
                                break;
                            case cmd.Dmi.HW_Bluetooth_Get_Status:
                                last_index = HW_Bluetooth_Get_Status;
                                break;
                            case cmd.Dmi.HW_Touch_Enable:
                                last_index = HW_Touch_Enable;
                                break;
                            case cmd.Dmi.HW_Touch_Disable:
                                last_index = HW_Touch_Disable;
                                break;
                            case cmd.Dmi.HW_Touch_Get_Status:
                                last_index = HW_Touch_Get_Status;
                                break;
                            case cmd.Dmi.HW_DozeMode_Enable:
                                last_index = HW_DozeMode_Enable;
                                break;
                            case cmd.Dmi.HW_DozeMode_Disable:
                                last_index = HW_DozeMode_Disable;
                                break;
                            case cmd.Dmi.HW_DozeMode_Get_Status:
                                last_index = HW_DozeMode_Get_Status;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.file
                    case cmd.file:
                        switch(selectedItem_index){
                            case cmd.File.writeUTF8ToFile:
                                last_index = writeUTF8ToFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/tt.txt");
                                label2.setText("String");
                                text2.setText("Unitech...");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.writeToFile:
                                last_index = writeToFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label2.setText("byte[]");
                                text2.setText("Unitech...");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.readFromFile:
                                last_index = readFromFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.copyFile:
                                last_index = copyFile;
                                label1.setText("From file path");
                                text1.setText("/sdcard/ff.txt");
                                label2.setText("Target file path");
                                text2.setText("/sdcard/Download/target.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.createFile:
                                last_index = createFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.deleteFile:
                                last_index = deleteFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.createFileStream:
                                last_index = createFileStream;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label2.setText("String to write to file");
                                text2.setText(String.format("Create time: %tc", new Date(System.currentTimeMillis())));
                                label3.setText("Keep file contents or not(true: keep, false: erase file)");
                                text3.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.exists:
                                last_index = exists;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.isDirectory:
                                last_index = isDirectory;
                                label1.setText("Folder path");
                                text1.setText("/sdcard/ff");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.list:
                                last_index = list;
                                label1.setText("Folder path");
                                text1.setText("/sdcard/ff");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.openFile:
                                last_index = openFile;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.renameTo:
                                last_index = renameTo;
                                label1.setText("File path");
                                text1.setText("/sdcard/ff.txt");
                                label2.setText("New name");
                                text2.setText("ff_new.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.canWrite:
                                last_index = canWrite;
                                label1.setText("File path: (Cannot Write: /data/data/com.unitech.stagego2/)");
                                text1.setText("/data/data/com.unitech.stagego2/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.File.canRead:
                                last_index = canRead;
                                label1.setText("File path: (Cannot Read: /data/data/com.unitech.stagego2/)");
                                text1.setText("/data/data/com.unitech.stagego2/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.fota
                    case cmd.fota:
                        switch(selectedItem_index){
                            case cmd.Fota.silentOSUpdate:
                                last_index = silentOSUpdate;
                                label1.setText("Update zip path:");
                                text1.setText("/sdcard/pp.zip");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.general
                    case cmd.general:
                        switch(selectedItem_index){
                            case cmd.General.setLanguage:
                                last_index = setLanguage;
                                label1.setText("ex:(zh-TW, en-US, ...)");
                                text1.setText("zh-TW");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.General.setImeCurrentKeyboard:
                                last_index = setImeCurrentKeyboard;
                                label1.setText(
                                        "Qurery cmd: adb shell ime list -a \n" +
                                        "Input PackageName or PackageName/ClassName \n" +
                                        "Ex: com.google.android.apps.inputmethod.zhuyin/.ZhuyinInputMethodService \n" +
                                        "or \n" +
                                        "com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME \n" +
                                        "or \n" +
                                        "com.google.android.inputmethod.pinyin/.PinyinIME\n" );
                                text1Multiline.setText("com.google.android.apps.inputmethod.zhuyin");
                                label1.setVisibility(View.VISIBLE);
                                text1Multiline.setVisibility(View.VISIBLE);
                                break;
                            case cmd.General.getDeviceSerialNumber:
                                last_index = getDeviceSerialNumber;
                                break;
                            case cmd.General.getDeviceBuildNumber:
                                last_index = getDeviceBuildNumber;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.keymap
                    case cmd.keymap:
                        switch(selectedItem_index){
                            case cmd.Keymap.enableKeyMapping_Enable:
                                last_index = enableKeyMapping_Enable;
                                break;
                            case cmd.Keymap.enableKeyMapping_Disable:
                                last_index = enableKeyMapping_Disable;
                                break;
                            case cmd.Keymap.addKeyMappings_Keycode:
                                last_index = addKeyMappings_Keycode;
                                label1.setText("Programmable Key");
                                text1.setText("Scan Left");
                                label2.setText("KeyCode");
                                text2.setText("25");
                                label3.setText("Broadcast Down Action");
                                text3.setText("action.bda");
                                label4.setText("Broadcast Down Parameters");
                                text4.setText("appname,Unote;autoStart,true");
                                label5.setText("Broadcast Up Action");
                                text5.setText("action.bua");
                                label6.setText("Broadcast Up Parameters");
                                text6.setText("appname,verone;autoStart,true");
                                label7.setText("Wakeup (enable, disable)");
                                text7.setText("disable");
                                label8.setText("startActivityParams (it need to set null in KeyCode.)");
                                text8.setText("null");

                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                label7.setVisibility(View.VISIBLE);
                                label8.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                text7.setVisibility(View.VISIBLE);
                                text8.setVisibility(View.VISIBLE);
                                text8.setEnabled(false);
                                break;
                            case cmd.Keymap.addKeyMappings_LaunchApp:
                                last_index = addKeyMappings_LaunchApp;
                                label1.setText("Programmable Key");
                                text1.setText("Scan Right");
                                label2.setText("App Data(classname and packagename, split with space.)");
                                text2.setText("com.unitech.unote.UNote com.unitech.unote");
                                label3.setText("Broadcast Down Action");
                                text3.setText("action.bda");
                                label4.setText("Broadcast Down Parameters");
                                text4.setText("appname,Unote;autoStart,true");
                                label5.setText("Broadcast Up Action");
                                text5.setText("action.bua");
                                label6.setText("Broadcast Up Parameters");
                                text6.setText("appname,verone;autoStart,true");
                                label7.setText("Wakeup (enable, disable)");
                                text7.setText("enable");
                                label8.setText("startActivityParams");
                                text8.setText("runapp,keymap;light,enable");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                label7.setVisibility(View.VISIBLE);
                                label8.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                text7.setVisibility(View.VISIBLE);
                                text8.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Keymap.resetKeyMappings:
                                last_index = resetKeyMappings;
                                break;
                            case cmd.Keymap.importKeyMappings:
                                last_index = importKeyMappings;
                                label1.setText("Folder path ");
                                text1.setText("/sdcard/kk/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Keymap.exportKeyMappings:
                                last_index = exportKeyMappings;
                                label1.setText("Folder path ");
                                text1.setText("/sdcard/kk/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.location
                    case cmd.location:
                        switch(selectedItem_index){
                            case cmd.Location.SW_setLocationMode_Enable:
                                last_index = SW_setLocationMode_Enable;
                                break;
                            case cmd.Location.SW_setLocationMode_Disable:
                                last_index = SW_setLocationMode_Disable;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.nfc
                    case cmd.nfc:
                        switch(selectedItem_index){
                            case cmd.Nfc.SW_nfc_Enable:
                                last_index = SW_nfc_Enable;
                                break;
                            case cmd.Nfc.SW_nfc_Disable:
                                last_index = SW_nfc_Disable;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.power
                    case cmd.power:
                        switch(selectedItem_index){
                            case cmd.Power.ColdBoot:
                                last_index = ColdBoot;
                                break;
                            case cmd.Power.WarmBoot:
                                last_index = WarmBoot;
                                break;
                            case cmd.Power.acquireWakeLock:
                                last_index = acquireWakeLock;
                                label1.setText("timeout (long) (Set to 0 then the WakeLock won't expire)");
                                text1.setText("0");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Power.releaseWakeLock:
                                last_index = releaseWakeLock;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.safemodelock
                    case cmd.safemodelock:
                        switch(selectedItem_index){
                            case cmd.Safemodelock.setSafeModeLock:
                                last_index = setSafeModeLock;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label2.setText("launcherPackageName (String) (If it set empty, it will clean default lancher setting.");
                                text2.setText("com.ute.eu.ELauncher");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;

                        }
                        break;
                    //endregion

                    // region cmd.scanner
                    case cmd.scanner:
                        switch(selectedItem_index){
                            case cmd.Scanner.importSettings:
                                last_index = importScannerSettings;
                                label1.setText("folder path: (ex: /sdcard/scanner/ )");
                                text1.setText("/sdcard/scanner/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Scanner.exportSettings:
                                last_index = exportScannerSettings;
                                label1.setText("folder path: (ex: /sdcard/scanner/ )");
                                text1.setText("/sdcard/scanner/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Scanner.importEaSeriesSettings:
                                last_index = importEaSeriesScannerSettings;
                                label1.setText("file path: (ex: /sdcard/scanner.conf )");
                                text1.setText("/sdcard/scanner.conf");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Scanner.exportEaSeriesSettings:
                                last_index = exportEaSeriesScannerSettings;
                                label1.setText("file path: (ex: /sdcard/scanner.conf )");
                                text1.setText("/sdcard/scanner.conf");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.uapps
                    case cmd.uapps:
                        switch(selectedItem_index){
                            case cmd.Uapps.Elauncher_setEnabled:
                                last_index = Elauncher_setEnabled;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.Elauncher_setKioskApp:
                                last_index = Elauncher_setKioskApp;
                                label1.setText("KioskApp PackageName (if enter empty data, it will clear setting)");
                                text1.setText("com.unitech.keyremap");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.Elauncher_setLauncherAppList:
                                last_index = Elauncher_setLauncherAppList;
                                label1.setText("Launcher App List ");
                                text1.setText("KeyRemap,Files,Chrome,Camera,Clock,OEMConfig,UNote");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.Elauncher_setWallpaper:
                                last_index = Elauncher_setWallpaper;
                                label1.setText("WallPaper file path");
                                text1.setText("/sdcard/test.png");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.Elauncher_setPINCode:
                                last_index = Elauncher_setPINCode;
                                label1.setText("PIN Code");
                                text1.setText("1122");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.Elauncher_setMode:
                                last_index = Elauncher_setMode;
                                label1.setText("Mode 0: Launcher mode, 1: Kiosk mode (Set KioskApp first)");
                                text1.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;

                            case cmd.Uapps.MoboLink_SetServerURL:
                                last_index = MoboLink_SetServerURL;
                                label1.setText("ServerURL");
                                text1.setText("https://mobolink.tw.ute.com/REST/OdmpServer");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetRebootstrap:
                                last_index = MoboLink_SetRebootstrap;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetShowUI:
                                last_index = MoboLink_SetShowUI;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetConnectionNotification:
                                last_index = MoboLink_SetConnectionNotification;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetEventNotification:
                                last_index = MoboLink_SetEventNotification;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetUserConfigurable:
                                last_index = MoboLink_SetUserConfigurable;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetAutoRun:
                                last_index = MoboLink_SetAutoRun;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.MoboLink_SetMoboLinkConfig:
                                last_index = MoboLink_SetMoboLinkConfig;
                                label1.setText("ServerURL");
                                text1.setText("https://mobolink.tw.ute.com/REST/OdmpServer");
                                label2.setText("Rebootstrap: enable (boolean) (true or false)");
                                text2.setText("true");
                                label3.setText("ShowUI: enable (boolean) (true or false)");
                                text3.setText("true");
                                label4.setText("ConnectionNotification: enable (boolean) (true or false)");
                                text4.setText("true");
                                label5.setText("EventNotification: enable (boolean) (true or false)");
                                text5.setText("true");
                                label6.setText("UserConfigurable: enable (boolean) (true or false)");
                                text6.setText("true");
                                label7.setText("AutoRun: enable (boolean) (true or false)");
                                text7.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                label7.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                text7.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.SoftwareUpdate_updateConfig:
                                last_index = SoftwareUpdate_updateConfig;
                                label1.setText("ServerURL");
                                text1.setText("https://mobolink.tw.ute.com/ususTest");
                                label2.setText("OPeration Mode (Normal Mode:0, Auto Mode:1, Auto Silent Mode:2, Disable:3)");
                                text2.setText("3");
                                label3.setText("Policy: Installed Applications (All:0, None:1, Selectd:2)");
                                text3.setText("2");
                                label4.setText("Policy: Installed List");
                                text4.setText("KeyRemap,Software Update,USS,UNote,StageGO,OEMConfig,AppCtrlService,selfupdate,Startup");
                                label5.setText("Policy: New applications (None:0, Install:1)");
                                text5.setText("1");
                                label6.setText("Passcode");
                                text6.setText("");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.SoftwareUpdate_exportSettings:
                                last_index = SoftwareUpdate_exportSettings;
                                label1.setText("Export Setting file path: (ex: /sdcard/config.txt )");
                                text1.setText("/sdcard/config.txt");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.SoftwareUpdate_importSettings:
                                last_index = SoftwareUpdate_importSettings;
                                label1.setText("Importsetting file path: (ex: /sdcard/33/ )");
                                text1.setText("/sdcard/33/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.SoftwareUpdate_resetSettings:
                                last_index = SoftwareUpdate_resetSettings;
                                label1.setText("Reset Setting");
                                label1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_setReportPath:
                                last_index = StageGo_setReportPath;
                                label1.setText("file path: (ex: /StageGO_Reports/ )");
                                text1.setText("/StageGO_Reports/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_setReportMaxNum:
                                last_index = StageGo_setReportMaxNum;
                                label1.setText("Report Max Number");
                                text1.setText("2");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_enabledBootupScript:
                                last_index = StageGo_enabledBootupScript;
                                label1.setText("enable (boolean) (true or false)");
                                text1.setText("true");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_setBootupScript:
                                last_index = StageGo_setBootupScript;
                                label1.setText("Bootup Script file path ('/' mean /sdcard/) (Files path, separated by ',')");
                                text1.setText("/111.stagego,/777.stagego");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_setPasscode:
                                last_index = StageGo_setPasscode;
                                label1.setText("Press any word (press emptn data, it will set no password)");
                                text1.setText("112233");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.StageGo_setScanMode:
                                last_index = StageGo_setScanMode;
                                label1.setText("ScanMode (CameraMode or ScannerMode)");
                                text1.setText("CameraMode");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.RFID2Key_importSettings:
                                last_index = RFID2Key_importSettings;
                                label1.setText("Complete file path: (ex: /sdcard/tt/gg.conf )");
                                text1.setText("/sdcard/tt/gg.conf");
                                label2.setText("passcode (It can be empty.)(ex:  ) (if conf file was not json file, it will ignore passcode.)");
                                text2.setText("");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.RFID2Key_exportSettings:
                                last_index = RFID2Key_exportSettings;
                                label1.setText("Complete file path: (ex: /sdcard/tt/gg.conf )");
                                text1.setText("/sdcard/tt/gg.conf");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.RFID2Key_resetSettings:
                                last_index = RFID2Key_resetSettings;
                                label1.setText("passcode (if it was no data, it can be empty.): (ex:  )");
                                text1.setText("");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Uapps.RFID2Key_setMode:
                                last_index = RFID2Key_setMode;
                                label1.setText("mode (true or false) (RFID mode:true, Scan mode:false)");
                                text1.setText("true");
                                label2.setText("passcode (if it was no data, it can be empty.): (ex:  )");
                                text2.setText("");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
//                            case cmd.Uapps.RFID2Key_updateSettings:
//                                last_index = RFID2Key_updateSettings;
//                                label1.setText("rfid2key (boolean) (ex: false )");
//                                text1.setText("false");
//                                label2.setText("quickSwitch (boolean) (ex: false )");
//                                text2.setText("false");
//                                label3.setText("quickSwitchTime (int) (ex: 250 )");
//                                text3.setText("250");
//                                label4.setText("readerOnce (boolean) (ex: true )");
//                                text4.setText("true");
//                                label5.setText("passcode (string) (ex: \"\")");
//                                text5.setText("");
//                                label6.setText("autoStart (boolean) (ex: false )");
//                                text6.setText("false");
//                                label7.setText("region (string) (ex: NA )");
//                                text7.setText("NA");
//                                label8.setText("readPower (int) (ex: 2600)");
//                                text8.setText("2600");
//                                label9.setText("readerTimeout (int) (ex: 1000)");
//                                text9.setText("1000");
//                                label10.setText("encoding (string) (ex: FM0)");
//                                text10.setText("FM0");
//                                label11.setText("session (string) (ex: S0)");
//                                text11.setText("S0");
//                                label12.setText("target (string) (ex: A)");
//                                text12.setText("A");
//                                label13.setText("q (string) (ex: DynamicQ)");
//                                text13.setText("DynamicQ");
//                                label14.setText("format (int) (ex: 1)");
//                                text14.setText("1");
//                                label15.setText("outputMethod (int) (ex: 0)");
//                                text15.setText("0");
//                                label16.setText("outputDuration (int) (ex: 200)");
//                                text16.setText("200");
//                                label17.setText("beep (boolean) (ex: true)");
//                                text17.setText("true");
//                                label18.setText("prefix (string) (ex: \"\")");
//                                text18.setText("");
//                                label19.setText("suffix (string) (ex: \"\")");
//                                text19.setText("");
//                                label20.setText("terminator (int) (ex: 1)");
//                                text20.setText("1");
//
//                                label1.setVisibility(View.VISIBLE);
//                                label2.setVisibility(View.VISIBLE);
//                                label3.setVisibility(View.VISIBLE);
//                                label4.setVisibility(View.VISIBLE);
//                                label5.setVisibility(View.VISIBLE);
//                                label6.setVisibility(View.VISIBLE);
//                                label7.setVisibility(View.VISIBLE);
//                                label8.setVisibility(View.VISIBLE);
//                                label9.setVisibility(View.VISIBLE);
//                                label10.setVisibility(View.VISIBLE);
//                                label11.setVisibility(View.VISIBLE);
//                                label12.setVisibility(View.VISIBLE);
//                                label13.setVisibility(View.VISIBLE);
//                                label14.setVisibility(View.VISIBLE);
//                                label15.setVisibility(View.VISIBLE);
//                                label16.setVisibility(View.VISIBLE);
//                                label17.setVisibility(View.VISIBLE);
//                                label18.setVisibility(View.VISIBLE);
//                                label19.setVisibility(View.VISIBLE);
//                                label20.setVisibility(View.VISIBLE);
//                                text1.setVisibility(View.VISIBLE);
//                                text2.setVisibility(View.VISIBLE);
//                                text3.setVisibility(View.VISIBLE);
//                                text4.setVisibility(View.VISIBLE);
//                                text5.setVisibility(View.VISIBLE);
//                                text6.setVisibility(View.VISIBLE);
//                                text7.setVisibility(View.VISIBLE);
//                                text8.setVisibility(View.VISIBLE);
//                                text9.setVisibility(View.VISIBLE);
//                                text10.setVisibility(View.VISIBLE);
//                                text11.setVisibility(View.VISIBLE);
//                                text12.setVisibility(View.VISIBLE);
//                                text13.setVisibility(View.VISIBLE);
//                                text14.setVisibility(View.VISIBLE);
//                                text15.setVisibility(View.VISIBLE);
//                                text16.setVisibility(View.VISIBLE);
//                                text17.setVisibility(View.VISIBLE);
//                                text18.setVisibility(View.VISIBLE);
//                                text19.setVisibility(View.VISIBLE);
//                                text20.setVisibility(View.VISIBLE);
//                                break;
                        }
                        break;
                    //endregion

                    // region cmd.security
                    case cmd.security:
                        switch(selectedItem_index){
                            case cmd.Security.setAdbDebugging_Enable:
                                last_index = setAdbDebugging_Enable;
                                break;
                            case cmd.Security.setAdbDebugging_Disable:
                                last_index = setAdbDebugging_Disable;
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.wlan
                    case cmd.wlan:
                        switch(selectedItem_index){
                            case cmd.Wlan.setWifiProfile:
                                last_index = setWifiProfile;
                                label1.setText("SSID (String)");
                                text1.setText("aaa");
                                label2.setText("Password (String)");
                                text2.setText("1234567");
                                label3.setText("SecurityMode (int) (None:0, WEP:1, WPA:2)");
                                text3.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.getWifiProfile:
                                last_index = getWifiProfile;
                                label1.setText("SSID (Only get SecurityMode, No password)(SecurityMode (int) (None:0, WEP:1, WPA:2))");
                                text1.setText("aaa");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setIpAssignmentProfile:
                                last_index = setIpAssignmentProfile;
                                label1.setText("SSID (String)");
                                text1.setText("Unitech");
                                label2.setText("IpAssignMent (int) (DHCP:0 , STATIC:1) (If set DHCP, other parameters is invalid.");
                                text2.setText("1");
                                label3.setText("IpAddress (String)");
                                text3.setText("10.10.100.13");
                                label4.setText("PrefixLength (int)");
                                text4.setText("24");
                                label5.setText("Gateway (String)");
                                text5.setText("10.10.0.1");
                                label6.setText("Dns1 (String)");
                                text6.setText("168.95.1.1");
                                label7.setText("Dns2 (String)");
                                text7.setText("168.95.192.3");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                label7.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                text7.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.getIpAssignmentProfile:
                                last_index = getIpAssignmentProfile;
                                label1.setText("SSID (String)");
                                text1.setText("Unitech");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setProxyProfile:
                                last_index = setProxyProfile;
                                label1.setText("SSID (String)");
                                text1.setText("Unitech");
                                label2.setText("ProxySetting (int) (None:0, Manual:1, Proxy Auto-Config:2) ");
                                text2.setText("0");
                                label3.setText("PacFileUrl (String)");
                                text3.setText("https://10.10.100.33");
                                label4.setText("Host (String)");
                                text4.setText("88");
                                label5.setText("Port(int)");
                                text5.setText("80");
                                label6.setText("ExcList(String)");
                                text6.setText("ttt");

                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                label5.setVisibility(View.VISIBLE);
                                label6.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                text5.setVisibility(View.VISIBLE);
                                text6.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.getProxyProfile:
                                last_index = getProxyProfile;
                                label1.setText("SSID (String)");
                                text1.setText("Unitech");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.deleteProfile:
                                last_index = deleteProfile;
                                label1.setText("SSID (String)");
                                text1.setText("aaa");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.isProfileExisted:
                                last_index = isProfileExisted;
                                label1.setText("SSID (String) (Existed: result is success)");
                                text1.setText("aaa");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.connectProfile:
                                last_index = connectProfile;
                                label1.setText("SSID (String)");
                                text1.setText("Unitech");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.getConnectedProfile:
                                last_index = getConnectedProfile;
                                break;
                            case cmd.Wlan.removeAllProfiles:
                                last_index = removeAllProfiles;
                                break;
                            case cmd.Wlan.isProfilesEmpty:
                                last_index = isProfilesEmpty;
                                break;
                            case cmd.Wlan.SW_Wifi_Enable:
                                last_index = SW_Wifi_Enable;
                                break;
                            case cmd.Wlan.SW_Wifi_Disable:
                                last_index = SW_Wifi_Disable;
                                break;
                            case cmd.Wlan.setWifiEnterpriseProfile:
                                last_index = setWifiEnterpriseProfile;
                                label1.setText("SSID (String)");
                                text1.setText("dlink-AE22");
                                label2.setText("CertFilePath (String)");
                                text2.setText("/sdcard/ca.pem");
                                label3.setText("Identity (String)");
                                text3.setText("test");
                                label4.setText("Password (String)");
                                text4.setText("asd");
                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setCaptivePortalMode:
                                last_index = setCaptivePortalMode;
                                label1.setText("CaptivePortalMode (int) (Don't detect:0, Detect and prompt:1, Auto disconnect:2)");
                                text1.setText("0");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setCaptivePortalUseHttps:
                                last_index = setCaptivePortalUseHttps;
                                label1.setText("CaptivePortalUseHttps (int) (Disable: 0, Enable: 1)");
                                text1.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setCaptivePortalHttpUrl:
                                last_index = setCaptivePortalHttpUrl;
                                label1.setText("CaptivePortalHttpUrl");
                                text1.setText("http://connectivitycheck.gstatic.com/generate_204");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setCaptivePortalHttpsUrl:
                                last_index = setCaptivePortalHttpsUrl;
                                label1.setText("CaptivePortalHttpsUrl");
                                text1.setText("https://www.google.com/generate_204");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.setWifiRandomizedMac:
                                last_index = setWifiRandomizedMac;
                                label1.setText("SSID (Need to reconnect specific WiFi)");
                                text1.setText("Unitech");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setText("Enabled");
                                text2.setText("true");
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.Wlan.getWifiRandomizedMacStatus:
                                last_index = getWifiRandomizedMacStatus;
                                label1.setText("SSID");
                                text1.setText("Unitech");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    //endregion

                    // region cmd.wlanAdvanced
                    case cmd.wlanadvanced:
                        switch(selectedItem_index){
                            case cmd.WlanAdvanced.importSettings:
                                last_index = importWlanAdvancedSettings;
                                label1.setText("folder path: (ex: /sdcard/77/ )");
                                text1.setText("/sdcard/77/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.WlanAdvanced.exportSettings:
                                last_index = exportWlanAdvancedSettings;
                                label1.setText("folder path: (ex: /sdcard/77/ )");
                                text1.setText("/sdcard/77/");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.WlanAdvanced.setNewSetting:
                                last_index = setNewWlanAdvancedSettings;
                                label1.setText("Power Save Mode (0 or 1)");
                                text1.setText("1");
                                label2.setText("Roaming Trigger (-10 ~ -120) (input 10 mean -10)");
                                text2.setText("10");
                                label3.setText("Roaming Delta (0 ~ 30)");
                                text3.setText("5");
                                label4.setText("Roaming Scan Period (0 ~ 60)");
                                text4.setText("8");

                                label1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);

                                break;
                        }
                        break;
                    // endregion+
                    case cmd.usu:
                    {
                        switch (selectedItem_index)
                        {
                            case cmd.USU.getPairingBarcode:
                                last_index=getPairingBarcode;
                                break;
                            case cmd.USU.getTargetScanner:
                                last_index=getTargetScanner;
                                break;
                            case cmd.USU.askScannerToUnpair:
                                last_index=askScannerToUnpair;
                                break;
                            case cmd.USU.getScannerSerialNumber:
                                last_index=getScannerSerialNumber;
                                break;
                            case cmd.USU.getScannerBluetoothName:
                                last_index=getScannerBluetoothName;
                                break;
                            case cmd.USU.getScannerBluetoothMacAddress:
                                last_index=getScannerBluetoothMacAddress;
                                break;
                            case cmd.USU.getScannerFirmwareVersion:
                                last_index=getScannerFirmwareVersion;
                                break;
                            case cmd.USU.getScannerBatteryLevel:
                                last_index=getScannerBatteryLevel;
                                break;
                            case cmd.USU.getScannerTriggerKey:
                                last_index=getScannerTriggerKey;
                                break;
                            case cmd.USU.setScannerTriggerKey:
                                last_index=setScannerTriggerKey;
                                label1.setText("0/1 to disable/enable trigger key");
                                text1.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.startDecode:
                                last_index=startDecode;
                                break;
                            case cmd.USU.stopDecode:
                                last_index=stopDecode;
                                break;
                            case cmd.USU.getDataACK:
                                last_index=getDataACK;
                                break;
                            case cmd.USU.setDataACK:
                                last_index=setDataACK;
                                label1.setText("0/1 to disable/enable data ack");
                                text1.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.getAutoConnection:
                                last_index=getAutoConnection;
                                break;
                            case cmd.USU.setAutoConnection:
                                last_index=setAutoConnection;
                                label1.setText("0/1 to disable/enable auto connect");
                                text1.setText("1");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.getScannerSymbologyConfiguration:
                                last_index=getScannerSymbologyConfiguration;
                                break;
                            case cmd.USU.setScannerSymbologyConfiguration:
                                last_index=setScannerSymbologyConfiguration;
                                label1.setText("Symbology Name");
                                text1.setText("");
                                label2.setText("Symbology Value");
                                text2.setText("");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.getScannerBtSignalCheckingLevel:
                                last_index=getScannerBtSignalCheckingLevel;
                                break;
                            case cmd.USU.setScannerBtSignalCheckingLevel:
                                last_index=setScannerBtSignalCheckingLevel;
                                label1.setText(" 0 is normal level, 1 is higher level");
                                text1.setText("0");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.getScannerDataTerminator:
                                last_index=getScannerDataTerminator;
                                break;
                            case cmd.USU.setScannerDataTerminator:
                                last_index=setScannerDataTerminator;
                                label1.setText("0= None/Null, 1=CR, 2=LF, 3=CRLF, 4=TAB");
                                text1.setText("3");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.enterIntoSsiMode:
                                last_index=enterIntoSsiMode;
                                break;
                            case cmd.USU.enterIntoRawMode:
                                last_index=enterIntoRawMode;
                                break;
                            case cmd.USU.receiveCurrentDataMode:
                                last_index=receiveCurrentDataMode;
                                break;
                            case cmd.USU.sendACKIndicator:
                                last_index=sendACKIndicator;
                                label1.setText("data ack(1= with Data ACK, 0 = indicator only)");
                                text1.setText("1");
                                label2.setText("beep time(0=no beep, 1=beep once, 2=beep twice, 3=beep 3 times)");
                                text2.setText("1");
                                label3.setText("vibrate(0= no vibrate, 1=vibrate)");
                                text3.setText("1");
                                label4.setText("led color(“none”=no led, ”red”=red led, “green”=green led,“blue”=blue led)");
                                text4.setText("red");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                label2.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                label3.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                label4.setVisibility(View.VISIBLE);
                                text4.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.exportSettings:
                                last_index=exportSettings;
                                label1.setText("file path (ex: /sdcard/Download/USU.conf)");
                                text1.setText("/sdcard/Download/USU.conf");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.importSettings:
                                last_index=importSettings;
                                label1.setText("file path (ex: /sdcard/Download/USU.conf)");
                                text1.setText("/sdcard/Download/USU.conf");
                                label1.setVisibility(View.VISIBLE);
                                text1.setVisibility(View.VISIBLE);
                                break;
                            case cmd.USU.uploadSettings:
                                last_index=uploadSettings;
                                break;
                        }
                    }
                        break;
                    // region usu
                    // endregion usu
                }


            } else {
                // Reselect the previous item, so OnItemSelected will be called again,
                // and everything will be reinitialized for this selection
//                if(selectedItem_index == -1 || position == 0) {
//                    button_go.setEnabled(false);
//                    cleanscreen(label1, text1, text1Multiline, label2, text2, label3, text3);
//                }
//                spinnerItem.setSelection(selectedCat_index);

                CleanScreen(label1, text1, text1Multiline, label2, text2, label3, text3);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    // region CleanScreen
    private void CleanScreen(TextView label1, EditText text1, EditText text1Multiline, TextView label2, EditText text2, TextView label3, EditText text3){


        TextView label4 = findViewById(R.id.label4);
        EditText text4 = findViewById(R.id.text4);
        TextView label5 = findViewById(R.id.label5);
        EditText text5= findViewById(R.id.text5);
        TextView label6 = findViewById(R.id.label6);
        EditText text6= findViewById(R.id.text6);
        TextView label7 = findViewById(R.id.label7);
        EditText text7 = findViewById(R.id.text7);
        TextView label8 = findViewById(R.id.label8);
        EditText text8 = findViewById(R.id.text8);
        TextView label9 = findViewById(R.id.label9);
        EditText text9 = findViewById(R.id.text9);
        TextView label10 = findViewById(R.id.label10);
        EditText text10 = findViewById(R.id.text10);
        TextView label11 = findViewById(R.id.label11);
        EditText text11 = findViewById(R.id.text11);
        TextView label12 = findViewById(R.id.label12);
        EditText text12 = findViewById(R.id.text12);
        TextView label13 = findViewById(R.id.label13);
        EditText text13 = findViewById(R.id.text13);
        TextView label14 = findViewById(R.id.label14);
        EditText text14 = findViewById(R.id.text14);
        TextView label15 = findViewById(R.id.label15);
        EditText text15 = findViewById(R.id.text15);
        TextView label16 = findViewById(R.id.label16);
        EditText text16 = findViewById(R.id.text16);
        TextView label17 = findViewById(R.id.label17);
        EditText text17 = findViewById(R.id.text17);
        TextView label18 = findViewById(R.id.label18);
        EditText text18 = findViewById(R.id.text18);
        TextView label19 = findViewById(R.id.label19);
        EditText text19 = findViewById(R.id.text19);
        TextView label20 = findViewById(R.id.label20);
        EditText text120 = findViewById(R.id.text20);


        CleanScreen(text1Multiline,
                    label1, text1,  label2, text2, label3, text3, label4, text4, label5, text5,
                    label6, text6, label7, text7, label8, text8, label9, text9, label10, text10,
                    label11, text11, label12, text12, label13, text13, label14, text14, label15, text15,
                    label16, text16, label17, text17, label18, text18, label19, text19, label20, text20);
    }

    private void CleanScreen(EditText text1Multiline,
                             TextView label1, EditText text1,
                             TextView label2, EditText text2,
                             TextView label3, EditText text3,
                             TextView label4, EditText text4,
                             TextView label5, EditText text5,
                             TextView label6, EditText text6,
                             TextView label7, EditText text7,
                             TextView label8, EditText text8,
                             TextView label9, EditText text9,
                             TextView label10, EditText text10,
                             TextView label11, EditText text11,
                             TextView label12, EditText text12,
                             TextView label13, EditText text13,
                             TextView label14, EditText text14,
                             TextView label15, EditText text15,
                             TextView label16, EditText text16,
                             TextView label17, EditText text17,
                             TextView label18, EditText text18,
                             TextView label19, EditText text19,
                             TextView label20, EditText text20){
        label1.setText("");
        text1.setText("");
        text1.setEnabled(true);
        text1Multiline.setText("");
        text1Multiline.setEnabled(true);
        label2.setText("");
        text2.setText("");
        text2.setEnabled(true);
        text3.setText("");
        text3.setEnabled(true);
        text4.setText("");
        text4.setEnabled(true);
        text5.setText("");
        text5.setEnabled(true);
        text6.setText("");
        text6.setEnabled(true);
        text7.setText("");
        text7.setEnabled(true);
        text8.setText("");
        text8.setEnabled(true);
        text9.setText("");
        text9.setEnabled(true);
        text10.setText("");
        text10.setEnabled(true);
        text11.setText("");
        text11.setEnabled(true);
        text12.setText("");
        text12.setEnabled(true);
        text13.setText("");
        text13.setEnabled(true);
        text14.setText("");
        text14.setEnabled(true);
        text15.setText("");
        text15.setEnabled(true);
        text16.setText("");
        text16.setEnabled(true);
        text17.setText("");
        text17.setEnabled(true);
        text18.setText("");
        text18.setEnabled(true);
        text19.setText("");
        text19.setEnabled(true);
        text20.setText("");
        text20.setEnabled(true);

        textview.setText("");

        label1.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text1Multiline.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        label4.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);
        label5.setVisibility(View.GONE);
        text5.setVisibility(View.GONE);
        label6.setVisibility(View.GONE);
        text6.setVisibility(View.GONE);
        label7.setVisibility(View.GONE);
        text7.setVisibility(View.GONE);
        label8.setVisibility(View.GONE);
        text8.setVisibility(View.GONE);
        label9.setVisibility(View.GONE);
        text9.setVisibility(View.GONE);
        label10.setVisibility(View.GONE);
        text10.setVisibility(View.GONE);
        label11.setVisibility(View.GONE);
        text11.setVisibility(View.GONE);
        label12.setVisibility(View.GONE);
        text12.setVisibility(View.GONE);
        label13.setVisibility(View.GONE);
        text13.setVisibility(View.GONE);
        label14.setVisibility(View.GONE);
        text14.setVisibility(View.GONE);
        label15.setVisibility(View.GONE);
        text15.setVisibility(View.GONE);
        label16.setVisibility(View.GONE);
        text16.setVisibility(View.GONE);
        label17.setVisibility(View.GONE);
        text17.setVisibility(View.GONE);
        label18.setVisibility(View.GONE);
        text18.setVisibility(View.GONE);
        label19.setVisibility(View.GONE);
        text19.setVisibility(View.GONE);
        label20.setVisibility(View.GONE);
        text20.setVisibility(View.GONE);
    }
    //endregion

    private boolean getBoolean(String data) throws InvalidParameterException {
        final String ENABLE = "enable";
        final String DISABLE = "disable";
        final String TRUE = "true";
        final String FALSE = "false";
        final String NO_1 = "1";
        final String NO_0 = "0";

        if(ENABLE.equals(data.toLowerCase() ) ) {
            return true;
        } else if(DISABLE.equals(data.toLowerCase() ) ) {
            return false;
        } else if(TRUE.equals(data.toLowerCase() ) ) {
            return true;
        } else if(FALSE.equals(data.toLowerCase() ) ) {
            return false;
        } else if(NO_1.equals(data.toLowerCase() ) ) {
            return true;
        } else if(NO_0.equals(data.toLowerCase() ) ) {
            return false;
        } else {
            throw new InvalidParameterException(data);
        }
    }

    // Expand the "/", "SD/" and "USB/" prefixes to real paths.
    public String getAbsolutePath(@NonNull String path) {
        String pathLowerCase = path.toLowerCase();
        if (pathLowerCase.startsWith("usb/") ) {
            return USB_PATH + path.substring(3);
        } else if (pathLowerCase.startsWith("sd/") ) {
            return SDCARD_PATH + path.substring(2);
        } else if (pathLowerCase.startsWith("/") ) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
        } else if (pathLowerCase.startsWith("flash/") ) {
            return FLASH_STORAGE + path.substring(5);
        } else {
            return null;
        }
    }

    private void  isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission READ_EXTERNAL_STORAGE is revoked");
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission WRITE_EXTERNAL_STORAGE is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        }
    }
    private Bundle[] tansInputDataToBundleArray(String inputStr) {
        Bundle[] resultBundleArray = null;

        try {
            if (TextUtils.isEmpty(inputStr) ) {
                return null;
            }

            String[] data = inputStr.split(";");
            resultBundleArray = new Bundle[data.length];

            for (int index = 0; index < data.length; index++) {
                String[] dataArray = data[index].split(",");
                Bundle bundle = new Bundle();
                bundle.putString("Key", dataArray[0]);
                resultBundleArray[index] = bundle;
                bundle.putString("Value", dataArray[1]);
                resultBundleArray[index] = bundle;
            }

            return resultBundleArray;
        } catch (Exception e) {
            return resultBundleArray;
        }
    }

}



