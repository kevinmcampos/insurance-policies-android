package me.kevincampos.presentation.mapper

interface ViewMapper<View, Domain> {

    fun mapToView(domain: Domain): View

}