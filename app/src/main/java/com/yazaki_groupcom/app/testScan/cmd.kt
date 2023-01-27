package com.yazaki_groupcom.app.testScan

import com.yazaki_groupcom.app.testScan.cmd
import com.yazaki_groupcom.app.testScan.cmd.Appmanagement
import com.yazaki_groupcom.app.testScan.cmd.Debugging
import com.yazaki_groupcom.app.testScan.cmd.Dmi
import com.yazaki_groupcom.app.testScan.cmd.Nfc
import com.yazaki_groupcom.app.testScan.cmd.Power
import com.yazaki_groupcom.app.testScan.cmd.Uapps
import com.yazaki_groupcom.app.testScan.cmd.Wlan
import com.yazaki_groupcom.app.testScan.cmd.WlanAdvanced
import com.yazaki_groupcom.app.testScan.cmd.USU

object cmd {
    const val apn = 1
    const val appmanagement = apn + 1
    const val audio = appmanagement + 1
    const val clock = audio + 1
    const val debugging = clock + 1
    const val display = debugging + 1
    const val dmi = display + 1
    const val file = dmi + 1
    const val fota = file + 1
    const val general = fota + 1
    const val keymap = general + 1
    const val location = keymap + 1
    const val nfc = location + 1
    const val power = nfc + 1
    const val safemodelock = power + 1
    const val scanner = safemodelock + 1
    const val uapps = scanner + 1
    const val security = uapps + 1
    const val wlan = security + 1
    const val wlanadvanced = wlan + 1
    const val usu = wlanadvanced + 1

    internal object Apn {
        const val ApnAddProfile = 1
        const val ApnDeleteProfile = ApnAddProfile + 1
        const val ApnProfileIsExist = ApnDeleteProfile + 1
        const val ApnUpdateProfile = ApnProfileIsExist + 1
        const val ApnDeleteAll = ApnUpdateProfile + 1
        const val ApnSetActiveProfile = ApnDeleteAll + 1
        const val ApnGetActivieProfileApnName = ApnSetActiveProfile + 1
        const val ApnGetNameList = ApnGetActivieProfileApnName + 1
        const val ApnGetProfile = ApnGetNameList + 1
        const val setAirplaneMode_Enable = ApnGetProfile + 1
        const val setAirplaneMode_Disable = setAirplaneMode_Enable + 1
    }

    internal object Appmanagement {
        const val installApp = 1
        const val removeApp = installApp + 1
        const val activateApp = removeApp + 1
        const val deactivateApp = activateApp + 1
        const val enableApp = deactivateApp + 1
        const val disableApp = enableApp + 1
        const val clearAppData = disableApp + 1
        const val getRunningAppNameList = clearAppData + 1
        const val getRunningPkgNameList = getRunningAppNameList + 1
        const val getInstallAppNameList = getRunningPkgNameList + 1
        const val getInstallPkgNameList = getInstallAppNameList + 1
        const val getBuiltinSystemAppNameList = getInstallPkgNameList + 1
        const val getBuiltinSystemPkgNameList = getBuiltinSystemAppNameList + 1
        const val getDisabledAppNameList = getBuiltinSystemPkgNameList + 1
        const val getDisabledPkgNameList = getDisabledAppNameList + 1
        const val osUpdate = getDisabledPkgNameList + 1
        const val runSysCmd = osUpdate + 1
        const val getAppInfoByAppName = runSysCmd + 1
        const val getAppInfoByPkgName = getAppInfoByAppName + 1
        const val setDefaultApp = getAppInfoByPkgName + 1
        const val clearDefaultApps_with_PackageNameArray = setDefaultApp + 1
        const val clearDefaultApps_Action_CategoriesArray =
            clearDefaultApps_with_PackageNameArray + 1
    }

    internal object Audio {
        const val setDefaultNotificationSound = 1
        const val setRingtoneSound = setDefaultNotificationSound + 1
        const val setDialPadTouchTones_Enable = setRingtoneSound + 1
        const val setDialPadTouchTones_Disable = setDialPadTouchTones_Enable + 1
        const val setTouchSound_Enable = setDialPadTouchTones_Disable + 1
        const val setTouchSound_Disable = setTouchSound_Enable + 1
        const val setVibrateOnTouch_Enable = setTouchSound_Disable + 1
        const val setVibrateOnTouch_Disable = setVibrateOnTouch_Enable + 1
        const val setVolume = setVibrateOnTouch_Disable + 1
    }

    internal object Clock {
        const val setNTPServer = 1
        const val getNTPServer = setNTPServer + 1
        const val setTimeMode_Enable = getNTPServer + 1
        const val setTimeMode_Disable = setTimeMode_Enable + 1
        const val getTimeMode = setTimeMode_Disable + 1
        const val setManualDate = getTimeMode + 1
        const val setManualTime = setManualDate + 1
        const val getManualTime = setManualTime + 1
        const val setTimeZoneMode_Enable = getManualTime + 1
        const val setTimeZoneMode_Disable = setTimeZoneMode_Enable + 1
        const val getTimeZoneMode = setTimeZoneMode_Disable + 1
        const val setTimeZone = getTimeZoneMode + 1
        const val getTimeZone = setTimeZone + 1
        const val setTimeFormat_24 = getTimeZone + 1
        const val setTimeFormat_12 = setTimeFormat_24 + 1
        const val getTimeFormat = setTimeFormat_12 + 1
    }

    internal object Debugging {
        const val saveLogcatFile = 1
        const val stopSaveLogcat = saveLogcatFile + 1
    }

    internal object Display {
        const val SetDisplayTimeout = 1
        const val GetDisplayTimeout = SetDisplayTimeout + 1
        const val SetStayAwake_Enable = GetDisplayTimeout + 1
        const val SetStayAwake_Disable = SetStayAwake_Enable + 1
        const val GetStayAwake = SetStayAwake_Disable + 1
        const val SetFontSize = GetStayAwake + 1
        const val GetFontSize = SetFontSize + 1
        const val SetScreenBacklightLevel = GetFontSize + 1
        const val GetScreenBacklightLevel = SetScreenBacklightLevel + 1
        const val SetAutoBrightness_Enable = GetScreenBacklightLevel + 1
        const val SetAutoBrightness_Disable = SetAutoBrightness_Enable + 1
        const val DisableScreenLock = SetAutoBrightness_Disable + 1
        const val SetAutoRotation_Enable = DisableScreenLock + 1
        const val SetAutoRotation_Disable = SetAutoRotation_Enable + 1
        const val setDisplayBatteryPercentage_Enable = SetAutoRotation_Disable + 1
        const val setDisplayBatteryPercentage_Disable = setDisplayBatteryPercentage_Enable + 1
        const val SetGloveMode_Enable = setDisplayBatteryPercentage_Disable + 1
        const val SetGloveMode_Disable = SetGloveMode_Enable + 1
        const val GetGloveMode = SetGloveMode_Disable + 1
        const val ShowLockScreenNotification = GetGloveMode + 1
        const val HideLockScreenNotification = ShowLockScreenNotification + 1
        const val SetScreenOrientationDisable = HideLockScreenNotification + 1
        const val SetScreenOrientationPortrait = SetScreenOrientationDisable + 1
        const val SetScreenOrientationLandscape = SetScreenOrientationPortrait + 1
        const val SetScreenOrientationReversePortrait = SetScreenOrientationLandscape + 1
        const val SetScreenOrientationReverseLandscape = SetScreenOrientationReversePortrait + 1
        const val SetScreenOrientationAuto = SetScreenOrientationReverseLandscape + 1
    }

    internal object Dmi {
        const val HW_Flash_Enable = 1
        const val HW_Flash_Disable = HW_Flash_Enable + 1
        const val HW_Flash_Get_Status = HW_Flash_Disable + 1
        const val HW_Camera_Enable = HW_Flash_Get_Status + 1
        const val HW_Camera_Disable = HW_Camera_Enable + 1
        const val HW_Camera_Get_Status = HW_Camera_Disable + 1
        const val HW_Keyboard_Enable = HW_Camera_Get_Status + 1
        const val HW_Keyboard_Disable = HW_Keyboard_Enable + 1
        const val HW_Keyboard_Get_Status = HW_Keyboard_Disable + 1
        const val HW_Usb_Enable = HW_Keyboard_Get_Status + 1
        const val HW_Usb_Disable = HW_Usb_Enable + 1
        const val HW_Usb_Get_Status = HW_Usb_Disable + 1
        const val HW_Usb_MTP_Mode_Enable = HW_Usb_Get_Status + 1
        const val HW_Usb_MTP_Mode_Disable = HW_Usb_MTP_Mode_Enable + 1
        const val HW_Usb_MTP_Mode_Get_Status = HW_Usb_MTP_Mode_Disable + 1
        const val HW_Gps_Enable = HW_Usb_MTP_Mode_Get_Status + 1
        const val HW_Gps_Disable = HW_Gps_Enable + 1
        const val HW_Gps_Get_Status = HW_Gps_Disable + 1
        const val HW_WWAN_Enable = HW_Gps_Get_Status + 1
        const val HW_WWAN_Disable = HW_WWAN_Enable + 1
        const val HW_WWAN_Get_Status = HW_WWAN_Disable + 1
        const val HW_Scanner_Enable = HW_WWAN_Get_Status + 1
        const val HW_Scanner_Disable = HW_Scanner_Enable + 1
        const val HW_Scanner_Get_Status = HW_Scanner_Disable + 1
        const val HW_Ime_Enable = HW_Scanner_Get_Status + 1
        const val HW_Ime_Disable = HW_Ime_Enable + 1
        const val HW_Ime_Get_Status = HW_Ime_Disable + 1
        const val HW_WLAN_Enable = HW_Ime_Get_Status + 1
        const val HW_WLAN_Disable = HW_WLAN_Enable + 1
        const val HW_WLAN_Get_Status = HW_WLAN_Disable + 1
        const val HW_Bluetooth_Enable = HW_WLAN_Get_Status + 1
        const val HW_Bluetooth_Disable = HW_Bluetooth_Enable + 1
        const val HW_Bluetooth_Get_Status = HW_Bluetooth_Disable + 1
        const val HW_Touch_Enable = HW_Bluetooth_Get_Status + 1
        const val HW_Touch_Disable = HW_Touch_Enable + 1
        const val HW_Touch_Get_Status = HW_Touch_Disable + 1
        const val HW_DozeMode_Enable = HW_Touch_Get_Status + 1
        const val HW_DozeMode_Disable = HW_DozeMode_Enable + 1
        const val HW_DozeMode_Get_Status = HW_DozeMode_Disable + 1
    }

    internal object File {
        const val writeUTF8ToFile = 1
        const val writeToFile = writeUTF8ToFile + 1
        const val readFromFile = writeToFile + 1
        const val copyFile = readFromFile + 1
        const val createFile = copyFile + 1
        const val deleteFile = createFile + 1
        const val createFileStream = deleteFile + 1
        const val exists = createFileStream + 1
        const val isDirectory = exists + 1
        const val list = isDirectory + 1
        const val openFile = list + 1
        const val renameTo = openFile + 1
        const val canWrite = renameTo + 1
        const val canRead = canWrite + 1
    }

    internal object Fota {
        const val silentOSUpdate = 1
    }

    internal object General {
        const val setLanguage = 1
        const val setImeCurrentKeyboard = setLanguage + 1
        const val getDeviceSerialNumber = setImeCurrentKeyboard + 1
        const val getDeviceBuildNumber = getDeviceSerialNumber + 1
    }

    internal object Keymap {
        const val enableKeyMapping_Enable = 1
        const val enableKeyMapping_Disable = enableKeyMapping_Enable + 1
        const val addKeyMappings_Keycode = enableKeyMapping_Disable + 1
        const val addKeyMappings_LaunchApp = addKeyMappings_Keycode + 1
        const val resetKeyMappings = addKeyMappings_LaunchApp + 1
        const val importKeyMappings = resetKeyMappings + 1
        const val exportKeyMappings = importKeyMappings + 1
    }

    internal object Location {
        const val SW_setLocationMode_Enable = 1
        const val SW_setLocationMode_Disable = SW_setLocationMode_Enable + 1
    }

    internal object Nfc {
        const val SW_nfc_Enable = 1
        const val SW_nfc_Disable = SW_nfc_Enable + 1
    }

    internal object Power {
        const val ColdBoot = 1
        const val WarmBoot = ColdBoot + 1
        const val acquireWakeLock = WarmBoot + 1
        const val releaseWakeLock = acquireWakeLock + 1
    }

    internal object Safemodelock {
        const val setSafeModeLock = 1
    }

    internal object Scanner {
        const val importSettings = 1
        const val exportSettings = importSettings + 1
        const val importEaSeriesSettings = exportSettings + 1
        const val exportEaSeriesSettings = importEaSeriesSettings + 1
    }

    internal object Uapps {
        const val Elauncher_setEnabled = 1
        const val Elauncher_setKioskApp = Elauncher_setEnabled + 1
        const val Elauncher_setLauncherAppList = Elauncher_setKioskApp + 1
        const val Elauncher_setWallpaper = Elauncher_setLauncherAppList + 1
        const val Elauncher_setPINCode = Elauncher_setWallpaper + 1
        const val Elauncher_setMode = Elauncher_setPINCode + 1
        const val MoboLink_SetServerURL = Elauncher_setMode + 1
        const val MoboLink_SetRebootstrap = MoboLink_SetServerURL + 1
        const val MoboLink_SetShowUI = MoboLink_SetRebootstrap + 1
        const val MoboLink_SetConnectionNotification = MoboLink_SetShowUI + 1
        const val MoboLink_SetEventNotification = MoboLink_SetConnectionNotification + 1
        const val MoboLink_SetUserConfigurable = MoboLink_SetEventNotification + 1
        const val MoboLink_SetAutoRun = MoboLink_SetUserConfigurable + 1
        const val MoboLink_SetMoboLinkConfig = MoboLink_SetAutoRun + 1
        const val SoftwareUpdate_updateConfig = MoboLink_SetMoboLinkConfig + 1
        const val SoftwareUpdate_exportSettings = SoftwareUpdate_updateConfig + 1
        const val SoftwareUpdate_importSettings = SoftwareUpdate_exportSettings + 1
        const val SoftwareUpdate_resetSettings = SoftwareUpdate_importSettings + 1
        const val StageGo_setReportPath = SoftwareUpdate_resetSettings + 1
        const val StageGo_setReportMaxNum = StageGo_setReportPath + 1
        const val StageGo_enabledBootupScript = StageGo_setReportMaxNum + 1
        const val StageGo_setBootupScript = StageGo_enabledBootupScript + 1
        const val StageGo_setPasscode = StageGo_setBootupScript + 1
        const val StageGo_setScanMode = StageGo_setPasscode + 1
        const val RFID2Key_importSettings = StageGo_setScanMode + 1
        const val RFID2Key_exportSettings = RFID2Key_importSettings + 1
        const val RFID2Key_resetSettings = RFID2Key_exportSettings + 1
        const val RFID2Key_setMode =
            RFID2Key_resetSettings + 1 //        public static final int RFID2Key_updateSettings = RFID2Key_setMode + 1;
    }

    internal object Security {
        const val setAdbDebugging_Enable = 1
        const val setAdbDebugging_Disable = setAdbDebugging_Enable + 1
    }

    internal object Wlan {
        const val setWifiProfile = 1
        const val getWifiProfile = setWifiProfile + 1
        const val setIpAssignmentProfile = getWifiProfile + 1
        const val getIpAssignmentProfile = setIpAssignmentProfile + 1
        const val setProxyProfile = getIpAssignmentProfile + 1
        const val getProxyProfile = setProxyProfile + 1
        const val deleteProfile = getProxyProfile + 1
        const val isProfileExisted = deleteProfile + 1
        const val connectProfile = isProfileExisted + 1
        const val getConnectedProfile = connectProfile + 1
        const val removeAllProfiles = getConnectedProfile + 1
        const val isProfilesEmpty = removeAllProfiles + 1 //100
        const val SW_Wifi_Enable = isProfilesEmpty + 1
        const val SW_Wifi_Disable = SW_Wifi_Enable + 1
        const val setWifiEnterpriseProfile = SW_Wifi_Disable + 1
        const val setCaptivePortalMode = setWifiEnterpriseProfile + 1
        const val setCaptivePortalUseHttps = setCaptivePortalMode + 1
        const val setCaptivePortalHttpUrl = setCaptivePortalUseHttps + 1
        const val setCaptivePortalHttpsUrl = setCaptivePortalHttpUrl + 1
        const val setWifiRandomizedMac = setCaptivePortalHttpsUrl + 1
        const val getWifiRandomizedMacStatus = setWifiRandomizedMac + 1
    }

    internal object WlanAdvanced {
        const val importSettings = 1
        const val exportSettings = importSettings + 1
        const val setNewSetting = exportSettings + 1
    }

    internal object USU {
        const val getPairingBarcode = 1
        const val getTargetScanner = getPairingBarcode + 1
        const val askScannerToUnpair = getTargetScanner + 1
        const val getScannerSerialNumber = askScannerToUnpair + 1
        const val getScannerBluetoothName = getScannerSerialNumber + 1
        const val getScannerBluetoothMacAddress = getScannerBluetoothName + 1
        const val getScannerFirmwareVersion = getScannerBluetoothMacAddress + 1
        const val getScannerBatteryLevel = getScannerFirmwareVersion + 1
        const val getScannerTriggerKey = getScannerBatteryLevel + 1
        const val setScannerTriggerKey = getScannerTriggerKey + 1
        const val startDecode = setScannerTriggerKey + 1
        const val stopDecode = startDecode + 1
        const val getDataACK = stopDecode + 1
        const val setDataACK = getDataACK + 1
        const val getAutoConnection = setDataACK + 1
        const val setAutoConnection = getAutoConnection + 1
        const val getScannerSymbologyConfiguration = setAutoConnection + 1
        const val setScannerSymbologyConfiguration = getScannerSymbologyConfiguration + 1
        const val getScannerBtSignalCheckingLevel = setScannerSymbologyConfiguration + 1
        const val setScannerBtSignalCheckingLevel = getScannerBtSignalCheckingLevel + 1
        const val getScannerDataTerminator = setScannerBtSignalCheckingLevel + 1
        const val setScannerDataTerminator = getScannerDataTerminator + 1
        const val enterIntoSsiMode = setScannerDataTerminator + 1
        const val enterIntoRawMode = enterIntoSsiMode + 1
        const val receiveCurrentDataMode = enterIntoRawMode + 1
        const val sendACKIndicator = receiveCurrentDataMode + 1
        const val exportSettings = sendACKIndicator + 1
        const val importSettings = exportSettings + 1
        const val uploadSettings = importSettings + 1
    } //region finish
    //    public static final int ApnAddProfile = 1;
    //    public static final int ApnDeleteProfile = ApnAddProfile + 1;
    //    public static final int ApnProfileIsExist = ApnDeleteProfile + 1;
    //    public static final int ApnUpdateProfile = ApnProfileIsExist + 1;
    //    public static final int ApnDeleteAll = ApnUpdateProfile + 1;
    //    public static final int ApnSetActiveProfile = ApnDeleteAll + 1;
    //    public static final int ApnGetActivieProfileApnName = ApnSetActiveProfile + 1;
    //    public static final int ApnGetNameList = ApnGetActivieProfileApnName + 1;
    //    public static final int ApnGetProfile = ApnGetNameList + 1;
    //    public static final int setAirplaneMode_Enable = ApnGetProfile + 1;
    //    public static final int setAirplaneMode_Disable = setAirplaneMode_Enable + 1;
    //
    //    // AppManagement 12
    //    public static final int installApp = setAirplaneMode_Disable + 1;
    //    public static final int removeApp = installApp + 1;
    //    public static final int activateApp = removeApp + 1;
    //    public static final int deactivateApp = activateApp + 1;
    //    public static final int enableApp = deactivateApp + 1;
    //    public static final int disableApp = enableApp + 1;
    //    public static final int clearAppData = disableApp + 1;
    //    public static final int getRunningAppNameList = clearAppData + 1;
    //    public static final int getInstallPkgNameList = getRunningAppNameList + 1;
    //    public static final int getBuiltinSystemAppNameList = getInstallPkgNameList + 1;
    //    public static final int getBuiltinSystemPkgNameList = getBuiltinSystemAppNameList + 1;
    //    public static final int getDisabledAppNameList = getBuiltinSystemPkgNameList + 1;
    //    public static final int getDisabledPkgNameList = getDisabledAppNameList + 1;
    //    public static final int osUpdate = getDisabledPkgNameList + 1;
    //    public static final int runSysCmd = osUpdate + 1;
    //    public static final int getAppInfoByAppName = runSysCmd + 1;
    //    public static final int getAppInfoByPkgName = getAppInfoByAppName + 1;
    //    public static final int setDefaultApp = getAppInfoByPkgName + 1;
    //    public static final int clearDefaultApps = setDefaultApp + 1;
    //    // Audio 31
    //    public static final int setDefaultNotificationSound = clearDefaultApps + 1;
    //    public static final int setRingtoneSound = setDefaultNotificationSound + 1;
    //    public static final int setDialPadTouchTones = setRingtoneSound + 1;
    //    public static final int setTouchSound = setDialPadTouchTones + 1;
    //    public static final int setVibrateOnTouch = setTouchSound + 1;
    //    // clock 37
    //    public static final int setNTPServer = setVibrateOnTouch + 1;
    //    public static final int getNTPServer = setNTPServer + 1;
    //    public static final int setTimeMode = getNTPServer + 1;
    //    public static final int getTimeMode = setTimeMode + 1;
    //    public static final int setManualDate = getTimeMode + 1;
    //    public static final int setManualTime = setManualDate + 1;
    //    public static final int getManualTime = setManualTime + 1;
    //    public static final int setTimeZoneMode = getManualTime + 1;
    //    public static final int getTimeZoneMode = setTimeZoneMode + 1;
    //    public static final int setTimeZone = getTimeZoneMode + 1;
    //    public static final int getTimeZone = setTimeZone + 1;
    //    public static final int setTimeFormat = getTimeZone + 1;
    //    public static final int getTimeFormat = setTimeFormat + 1;
    //    // debugging 50
    //    public static final int saveLogcatFile = getTimeFormat + 1;
    //    public static final int stopSaveLogcat = saveLogcatFile + 1;
    //    // display 52
    //    public static final int SetDisplayTimeout = stopSaveLogcat + 1;
    //    public static final int GetDisplayTimeout = SetDisplayTimeout + 1;
    //    public static final int SetStayAwake = GetDisplayTimeout + 1;
    //    public static final int GetStayAwake = SetStayAwake + 1;
    //    public static final int SetFontSize = GetStayAwake + 1;
    //    public static final int GetFontSize = SetFontSize + 1;
    //    public static final int SetScreenBacklightLevel = GetFontSize + 1;
    //    public static final int GetScreenBacklightLevel = SetScreenBacklightLevel + 1;
    //    public static final int SetAutoBrightness = GetScreenBacklightLevel + 1;
    //    public static final int DisableScreenLock = SetAutoBrightness + 1;
    //    public static final int SetAutoRotation = DisableScreenLock + 1;
    //
    //    // dmi 63
    //    public static final int Hw_enable_flash = SetAutoRotation + 1;
    //    public static final int Hw_disable_flash = Hw_enable_flash + 1;
    //    public static final int Hw_get_flash_status = Hw_disable_flash + 1;
    ////    public static final int
    ////    public static final int
    ////    public static final int
    ////    public static final int
    ////    public static final int
    //
    //    // file 66
    //    public static final int writeUTF8ToFile = Hw_get_flash_status + 1;
    //    public static final int writeToFile = writeUTF8ToFile + 1;
    //    public static final int readFromFile = writeToFile + 1;
    //    public static final int copyFile = readFromFile + 1;
    //
    //    // fota 70
    //    public static final int silentOSUpdate = copyFile + 1;
    //    // general 71
    //    public static final int setLanguage = silentOSUpdate + 1;
    //    public static final int setImeCurrentKeyboard = setLanguage + 1;
    //
    //    // keymap 73
    //    public static final int enableKeyMapping = setImeCurrentKeyboard + 1;
    //    public static final int addKeyMappings = enableKeyMapping + 1;
    //    public static final int resetKeyMappings = addKeyMappings + 1;
    //    public static final int importKeyMappings = resetKeyMappings + 1;
    //    public static final int exportKeyMappings = importKeyMappings + 1;
    //    // location 78
    //    public static final int SW_enable_setLocationMode = exportKeyMappings + 1;
    //    public static final int SW_disable_setLocationMode = SW_enable_setLocationMode + 1;
    //
    //    // nfc 80
    //    public static final int SW_enable_nfc = SW_disable_setLocationMode + 1;
    //    public static final int SW_disable_nfc = SW_enable_nfc + 1;
    //
    //    // power 82
    //    public static final int ColdBoot = SW_disable_nfc + 1;
    //    public static final int WarmBoot = ColdBoot + 1;
    //
    //    // safemodelock 84
    //    public static final int setSafeModeLock = WarmBoot + 1;
    //    // scanner 85
    //    public static final int importSettings = setSafeModeLock + 1;
    //    public static final int exportSettings = importSettings + 1;
    //    public static final int importEaSeriesSettings = exportSettings + 1;
    //    public static final int exportEaSeriesSettings = importEaSeriesSettings + 1;
    //
    //    // security 89
    //    public static final int setAdbDebugging = exportEaSeriesSettings + 1;
    //
    //    // wlan 90
    //    public static final int setWifiProfile = setAdbDebugging + 1;
    //    public static final int getWifiProfile = setWifiProfile + 1;
    //    public static final int setIpAssignmentProfile = getWifiProfile + 1;
    //    public static final int getIpAssignmentProfile = setIpAssignmentProfile + 1;
    //    public static final int setProxyProfile = getIpAssignmentProfile + 1;
    //    public static final int getProxyProfile = setProxyProfile + 1;
    //    public static final int deleteProfile = getProxyProfile + 1;
    //    public static final int isProfileExisted = deleteProfile + 1;
    //    public static final int connectProfile = isProfileExisted + 1;
    //    public static final int getConnectedProfile = connectProfile + 1;
    //    public static final int removeAllProfiles = getConnectedProfile + 1;
    //    public static final int isProfilesEmpty = removeAllProfiles + 1;  //100
    // endregion
}