package com.yazaki_groupcom.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yazaki_groupcom.app.databinding.ActivityTestScanServiceBinding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.security.InvalidParameterException
import java.sql.Date
import java.util.ArrayList

/**
 * 以下都是运用unitech的sdk中的api
 */
import com.unitech.api.apn.ApnCtrl

import com.unitech.api.app.AppManagementCtrl

import com.unitech.api.audio.AudioCtrl

import com.unitech.api.clock.ClockCtrl

import com.unitech.api.debugging.DebuggingCtrl

import com.unitech.api.display.DisplayCtrl

import com.unitech.api.dmi.DmiCtrl

import com.unitech.api.file.FileCtrl

import com.unitech.api.fota.FotaCtrl

import com.unitech.api.general.GeneralCtrl

import com.unitech.api.keymap.KeymappingCtrl

import com.unitech.api.location.LocationCtrl

import com.unitech.api.nfc.NfcCtrl

import com.unitech.api.power.PowerCtrl

import com.unitech.api.safemodelock.SafeModeLockCtrl

import com.unitech.api.scanner.ScannerCtrl

import com.unitech.api.security.SecurityCtrl

import com.unitech.api.uapps.ElauncherCtrl

import com.unitech.api.uapps.MoboLinkCtrl

import com.unitech.api.uapps.RFID2KeyCtrl

import com.unitech.api.uapps.SoftwareUpdateCtrl

import com.unitech.api.uapps.StageGoCtrl

import com.unitech.api.usu.USUCtrl

import com.unitech.api.wlan.WlanAdvancedCtrl

import com.unitech.api.wlan.WlanCtrl


class TestScanServiceActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScanServiceActivity"
    }

    private lateinit var binding: ActivityTestScanServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScanServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Unitech Scan Serviceの開始
        binding.tvStart.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.start"
                sendBroadcast(intent)
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
            }
        }

        //Scan To Keyを終了する
        binding.tvScan2keyFalse.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.scan2key_setting"
                intent.putExtras(Bundle().apply {
                    putBoolean("scan2key", false)
                })
                sendBroadcast(intent)
                Toast.makeText(this, "scan2key_setting : scan2key, false", Toast.LENGTH_SHORT).show()
            }
        }

        //Scan To Keyを有効
        binding.tvScan2keyTrue.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.scan2key_setting"
                intent.putExtra("scan2key", true)
                sendBroadcast(intent)
                Toast.makeText(this, "scan2key_setting : scan2key, true", Toast.LENGTH_SHORT).show()
            }
        }

        //Unitech Scan Serviceの終了
        binding.tvClose.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.close"
                intent.putExtra("close", true)
                sendBroadcast(intent)
                Toast.makeText(this, "close : close, true", Toast.LENGTH_SHORT).show()
            }
        }
    }
}