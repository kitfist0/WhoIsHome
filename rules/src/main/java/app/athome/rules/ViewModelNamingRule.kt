package app.athome.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.getSuperNames

class ViewModelNamingRule : Rule() {

    companion object {
        private const val VIEW_MODEL = "ViewModel"
        private const val DESCRIPTION = "ViewModel names should end with ViewModel"
        private fun getMessage(name: String?) =
            "The ViewModel class name $name should have been ${name}ViewModel."
    }

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        DESCRIPTION,
        Debt.FIVE_MINS
    )

    override fun visitClass(klass: KtClass) {
        takeUnless { klass.getSuperNames().find { it == VIEW_MODEL }.isNullOrBlank() }
            ?.takeIf { klass.name?.endsWith(VIEW_MODEL) == false }
            ?.report(
                CodeSmell(issue, Entity.from(klass), getMessage(klass.name))
            )
    }
}
