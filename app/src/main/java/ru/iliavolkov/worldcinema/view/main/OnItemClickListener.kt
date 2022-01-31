package ru.iliavolkov.worldcinema.view.main

import ru.iliavolkov.worldcinema.model.FilmInfoDTO

interface OnItemClickListener {
    fun onItemClick(film: FilmInfoDTO)
}