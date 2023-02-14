package com.yazaki_groupcom.app.ui.rfidLogin

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcF
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityRfidLoginBinding
import com.yazaki_groupcom.app.ui.main.MainActivity

class RfidLoginActivity : BaseActivity() {
    companion object {
        const val TAG: String = "RfidLoginActivity"
    }

    private lateinit var binding: ActivityRfidLoginBinding

    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRfidLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this@RfidLoginActivity, MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition( android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
            finish()
        }

        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this)
            Log.e(TAG, "onCreate: nfcAdapter　create")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: nfcAdapter", e)
        }



        //NFC読み取り開始
        binding.tvQrMessage.setOnClickListener {
            try {
                if (nfcAdapter != null && nfcAdapter.isEnabled) {
                    val intent = Intent(this, javaClass)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
                    nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)

                    Toast.makeText(this, "RFID読み取り", Toast.LENGTH_SHORT).show()
                    //Log.e(TAG,"NFC is start" )
                } else {
                    // NFC is not enabled, show a message to the user
                    Toast.makeText(this,"RFID読み取りは使用できません", Toast.LENGTH_SHORT).show()
                    //Log.e(TAG, "NFC is not enabled")
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: readNfcButton", e)
            }
        }
    }


    override fun onResume() {
        super.onResume()

        //自動NFC読み取り開始
        try {
            //Log.e(TAG, "onResume: nfcAdapter　try")
            if (nfcAdapter != null && nfcAdapter.isEnabled) {
                //Log.e(TAG, "onResume: nfcAdapter　isEnabled")
                val intent = Intent(this, javaClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)

                Toast.makeText(this, "RFID読み取り", Toast.LENGTH_SHORT).show()
                //Log.e(TAG,"NFC is start" )
            } else {
                // NFC is not enabled, show a message to the user
                Toast.makeText(this,"RFID読み取りは使用できません", Toast.LENGTH_SHORT).show()
                //Log.e(TAG, "NFC is not enabled")
            }
        } catch (e: Exception) {
            Log.e(TAG, "onResume: readNfcButton", e)
        }
    }

    //
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

            val id = tag?.id

            //Toast.makeText(this, id!!.toList().toString(), Toast.LENGTH_SHORT).show()
            if (id != null) {
                binding.etQrCode.setText(id.toList().toString())
            }

            //获取卡的id编号
            id?.forEach { str ->
                Log.i(TAG, "读取NFC卡的 id 编号: id foreach :$str")
            }

            //获取卡的更详细的资料
            val techList = tag?.techList
            if (techList != null) {
                for (teach in techList){
                    Log.i(TAG, "读取NFC卡的 详细资料: teach:$teach")

                    //如果是：Suicaのカード : android.nfc.tech.NfcF
                    if(teach.equals("android.nfc.tech.NfcF")){
                        procFelica(intent)
                    }
                }
            }

        }

        //得到所有读取到的 NDEF 消息。
        if (intent != null) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    ?.also { rawMessages ->
                        val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                        // Process the messages array.

                        Log.e( TAG,"onNewIntent: NfcAdapter.ACTION_NDEF_DISCOVERED getMessages : $messages"
                        )
                    }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            if (nfcAdapter != null) {
                nfcAdapter.disableForegroundDispatch(this)
                Log.i(TAG, "NFC is disableForegroundDispatch ok")
            }
        } catch (e: Exception) {
            Log.e(TAG, "onPause: nfcAdapter disableForegroundDispatch", e)
        }
    }

    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent =
            Intent(this@RfidLoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Felica Cardの処理 对应suica的详细处理
     *
     * @param intent
     */
    private fun procFelica(intent: Intent) {
        try {
            val idm = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag?
            val nfcF: NfcF = NfcF.get(tag)
            val systemCode: ByteArray = nfcF.systemCode

            Log.i(TAG, "procFelica: idm:$idm")
            Log.i(TAG, "procFelica: tag:$tag")
            Log.i(TAG, "procFelica: nfcF:$nfcF")
            Log.i(TAG, "procFelica: systemCode:$systemCode")
            idm?.forEach {
                Log.i(TAG, "procFelica: idm:$it")
            }

            Log.i(TAG, "procFelica: nfcF.isConnected:${nfcF.isConnected}")
            systemCode.forEach {
                Log.i(TAG, "procFelica: systemCode:$it")
            }

            //对于systemCode的转换处理
            Log.i(TAG, "procFelica: systemCode.toUByteArray:${systemCode.toUByteArray()}", )
            Log.i(TAG, "procFelica: bytesToInt(systemCodeInt) : "+bytesToInt(systemCode) )

        } catch (e: Exception) {
            Log.e(TAG, "procFelica: Exception", e)
        }
    }


    fun bytesToInt(bytes: ByteArray): Int {
        return (bytes[0].toInt() and 0xff shl 8) or
                (bytes[1].toInt() and 0xff)
    }
}