package ru.iliavolkov.worldcinema.view.main.mainscreen

import ru.iliavolkov.worldcinema.model.FilmInfoDTO

interface OnItemClickListener {
    fun onItemClick(film: FilmInfoDTO)
}