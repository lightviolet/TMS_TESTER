package me.dy.tmstester.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tms.sdk.ITMSConsts
import com.tms.sdk.TMS
import com.tms.sdk.api.request.DeviceCert
import com.tms.sdk.api.request.Login
import com.tms.sdk.api.request.SetConfig
import com.tms.sdk.common.util.TMSUtil
import kotlinx.android.synthetic.main.activity_startup.*
import me.dy.tmstester.R

class StartupActivity : AppCompatActivity() {

    private var mTms = TMS.getInstance(applicationContext)

    init {
        initTms()
        tv_api_url.text = TMSUtil.getServerUrl(applicationContext)
        tv_project_number.text = TMSUtil.getGCMProjectId(applicationContext)
        tv_push_token.text = TMSUtil.getGCMToken(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        testProcess()
    }

    private fun initTms() {
        mTms.setPopupSetting(false, getString(R.string.app_name))
        mTms.setPopupNoti(true)
        mTms.setRingMode(true)
        mTms.setVibeMode(true)
        mTms.setIsPopupActivity(false)
        mTms.setNotiOrPopup(false)
    }

    private fun testProcess() {
        tv_status.text = "Initializing processing : deviceCert"
        tv_device_cert.text = "Processing..."
        DeviceCert(applicationContext).request{code, json -> run {
            tv_device_cert.text = json.toString()
            tv_project_number.text = TMSUtil.getGCMProjectId(applicationContext)
            tv_push_token.text = TMSUtil.getGCMToken(applicationContext)
            if (ITMSConsts.CODE_SUCCESS.equals(code)) {
                tv_status.text = "Initializing processing : login"
                tv_login.text = "Processing..."
                Login(applicationContext).request("FCMTESTUSER", null, {code, json -> run {
                    tv_login.text = json.toString()
                    if (ITMSConsts.CODE_SUCCESS.equals(code)) {
                        tv_status.text = "Initializing processing : setConfig"
                        tv_set_config.text = "Processing..."
                        SetConfig(applicationContext).request("Y", "Y", {code, json -> run {
                            tv_set_config.text = json.toString()
                            if (ITMSConsts.CODE_SUCCESS.equals(code)) {
                                tv_status.text = "Initializing success."
                            } else {
                                tv_status.text = "Initializing failed."
                            }
                        }})
                    } else {
                        tv_status.text = "Initializing failed."
                    }
                    }})
            } else {
                tv_status.text = "Initializing failed."
            }
        }}
    }

    override fun onDestroy() {
        super.onDestroy()
        TMS.clear()
    }
}

