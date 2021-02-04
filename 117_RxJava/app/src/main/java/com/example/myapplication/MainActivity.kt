package com.dicoding.myreactiveform

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.myreactiveform.databinding.ActivityMainBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menambahkan dataStream dari inputan email dgn subscribe ke stream tsb
        val emailStream = RxTextView.textChanges(binding.edEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe { showEmailExistAlert(it) }

        // Menambahkan dataStream dari inputan password dgn subscribe ke stream tsb
        val passwordStream = RxTextView.textChanges(binding.edPassword)
            .skipInitialValue()
            .map {
                password -> password.length < 6
            }
        passwordStream.subscribe{
            showPasswordMinimalAlert(it)
        }

        // mengecek pada dua inputan sekaligus, dengan operator Observable.merge
        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.edPassword)
                .map { password -> password.toString() != binding.edConfirmPassword.text.toString() },
            RxTextView.textChanges(binding.edConfirmPassword)
                .map { confirmPassword -> confirmPassword.toString() != binding.edPassword.text.toString() }
        )
        passwordConfirmationStream.subscribe{
            showPasswordConfirmationAlert(it)
        }

        // membaca dataStream dan menentukan tombolRegister dengan operator Observable.combineLatest
        val invalidFieldStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            Function3{ emailInvalid: Boolean, passwordInvalid:Boolean, passwordConfirmation:Boolean ->
                !emailInvalid && !passwordConfirmation && !passwordInvalid
            }
        )
        invalidFieldStream.subscribe{ isValid -> if(isValid){
                binding.btnRegister.isEnabled = true
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            }else {
                binding.btnRegister.isEnabled = false
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            }
        }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.edConfirmPassword.error = if (isNotValid) getString(R.string.password_not_same) else null
    }
}
