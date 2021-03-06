package tech.torah.aldis.androidapp.dataClassesAndInterfaces

import tech.torah.aldis.androidapp.R

/**
 * This enum was originally intended for a 3-tab layout, but the design was reconsidered.
 * It now functions as an enum to enumerate different subjects of which a shiur can be filtered
 * */
enum class ShiurFilterOption(val nameStringResourceId: Int) {
    ID(R.string.id),
    TITLE(R.string.tab_name_none),
    CATEGORY(R.string.tab_name_category),
    SPEAKER(R.string.tab_name_speaker),
    SERIES(R.string.tab_name_series),
    LENGTH(R.string.length),
    LANGUAGE(R.string.language),
    DATE_UPLOADED(R.string.date_uploaded),
    HAS_ATTACHMENT(R.string.has_attachment),
    HAS_DESCRIPTION(R.string.has_description),
    DATE_ADDED_TO_PERSONAL_COLLECTION(R.string.date_added_to_personal_collection),

    //TODO should speaker be before category or vice versa?

}