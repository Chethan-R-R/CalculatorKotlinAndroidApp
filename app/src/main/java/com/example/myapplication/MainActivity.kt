package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val dataToDisplayObj: DataToDisplay = DataToDisplay(result = "0", enterText = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.layoutDataToDisplay = dataToDisplayObj
        if(savedInstanceState!=null){
        dataToDisplayObj.result=savedInstanceState.getString("result","")
        dataToDisplayObj.enterText=savedInstanceState.getString("enterText","")
        }
        val buttonIds = arrayListOf<Button>(
            binding.btnZero,
            binding.btnOne,
            binding.btnTwo,
            binding.btnThree,
            binding.btnFour,
            binding.btnFive,
            binding.btnSix,
            binding.btnSeven,
            binding.btnEight,
            binding.btnNine,
            binding.btnPlus,
            binding.btnMinus,
            binding.btnMul,
            binding.btnDivide,
            binding.delete,
            binding.equal
        )
        for ((index, btn) in buttonIds.withIndex()) {
            btn.setOnClickListener {
                if (index < 10) {
                    updateEnterText(index.toString())

                } else {
                    when (index) {
                        10 -> updateEnterText("+")
                        11 -> updateEnterText("-")
                        12 -> updateEnterText("x")
                        13 -> updateEnterText("/")
                        14 -> dataToDisplayObj.enterText = ""
                        15 -> calulateResult()

                    }
                }
            }
        }
    }

    private fun updateEnterText(value: String) {
        dataToDisplayObj?.enterText = dataToDisplayObj.enterText + value
        binding.invalidateAll()
    }

    private fun calulateResult() {
        var first = "0"
        var second = "0"
        var sign = '+'
        var issecond = false
        try {
            for (i in dataToDisplayObj.enterText.toString() + 'E') {
                if ((i == '+' || i == '-' || i == 'x' || i == '/' || i == 'E') && issecond) {
                    when (sign) {

                        '+' -> first = (first.toInt() + second.toInt()).toString()
                        '-' -> first = (first.toInt() - second.toInt()).toString()
                        'x' -> first = (first.toInt() * second.toInt()).toString()
                        '/' -> first = (first.toFloat() / second.toFloat()).toString()
                    }
                    sign = i
                    second = ""
                } else if ((i == '+' || i == '-' || i == 'x' || i == '/') && !issecond) {
                    sign = i
                    issecond = true
                } else if (i !== '+' && i !== '-' && i !== 'x' && i !== '/' && issecond) {
                    second += i.toString()
                } else if (i !== '+' && i !== '-' && i !== 'x' && i !== '/' && !issecond) {
                    first += i.toString()
                }
            }
            dataToDisplayObj.result = first
            dataToDisplayObj.enterText = ""
            binding.invalidateAll()
        } catch (err: Throwable) {
            dataToDisplayObj.result = "invalid"
            dataToDisplayObj.enterText = ""
            binding.invalidateAll()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("result",dataToDisplayObj.result)
        outState.putString("enterText",dataToDisplayObj.enterText)
    }
}