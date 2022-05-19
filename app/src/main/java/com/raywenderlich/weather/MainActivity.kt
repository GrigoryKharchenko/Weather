package com.raywenderlich.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Weather)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // переменная для выполнения действий над фрагментами
        val manager: FragmentManager = supportFragmentManager
        // FragmentTransaction добавлять, удалять, заменять и выполнять другие действия с фрагментами в ответ на действия пользователя
        val transaction: FragmentTransaction = manager.beginTransaction()
        //добавляет фрагмент
        transaction.add(R.id.container, WeatherFragment.newInstance(), WeatherFragment.TAG)
        //При вызове addToBackStack() транзакция замены сохраняется в обратном стеке,
        // поэтому пользователь может отменить транзакцию и вернуть предыдущий фрагмент, нажав кнопку «Назад».
        transaction.addToBackStack(null)
        //commit вызывается последним,
        // несколько фрагментов в один и тот же контейнер, порядок их добавления определяет порядок их отображения в иерархии представлений
        transaction.commit()
    }
}
