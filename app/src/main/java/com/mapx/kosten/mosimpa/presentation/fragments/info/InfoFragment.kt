package com.mapx.kosten.mosimpa.presentation.fragments.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mapx.kosten.mosimpa.R
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * A simple [Fragment] subclass.
 */
class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_info_version.text = getVersionName()
    }

    private fun getVersionName(): String {
        val unknown = "UNKNOWN"
        val pInfo = this.activity?.packageManager?.getPackageInfo(
            this.activity?.packageName ?: unknown,
            0
        )
        val version = pInfo?.versionName ?: unknown
        return context?.resources?.getString(R.string.info_app_version, version) ?: unknown
    }
}
