package com.yazaki_groupcom.app

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestNfcBinding

class TestNfcActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestNfcActivity"
    }
    private lateinit var nfcAdapter: NfcAdapter

    private lateinit var binding: ActivityTestNfcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestNfcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        //开始读NFC
        binding.readNfcButton.setOnClickListener {
            if (nfcAdapter != null && nfcAdapter.isEnabled) {
                val intent = Intent(this, javaClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)

                Toast.makeText(this, "NFC is start", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "NFC is start" )
            } else {
                // NFC is not enabled, show a message to the user
                Toast.makeText(this, "NFC is not enabled, show a message to the user", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "NFC is not enabled" )
            }
        }
    }

    //
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val id = tag?.id

            Toast.makeText(this, id!!.toList().toString(), Toast.LENGTH_SHORT).show()

            //获取卡的id编号
            id?.forEach { str ->
                Log.e(TAG, "读取NFC卡的 id 编号: id foreach :$str")
            }

        }

        //？
        if (intent != null) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                    val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                    // Process the messages array.

                    Log.e(TAG, "onNewIntent: messages : $messages")
                }
            }
        }
    }

    //？
    override fun onPause() {
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this)
            Log.e(TAG, "NFC is disableForegroundDispatch" )
        }
    }
}