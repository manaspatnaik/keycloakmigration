package de.klg71.keycloakmigration.changeControl.actions.realm

import de.klg71.keycloakmigration.changeControl.actions.Action
import de.klg71.keycloakmigration.changeControl.actions.MigrationException
import de.klg71.keycloakmigration.model.Realm
import de.klg71.keycloakmigration.rest.realmById
import de.klg71.keycloakmigration.rest.realmExistsById

class UpdateRealmAction(
        private val id: String,
        private val realmName: String? = null,
        private val displayName: String? = null,
        private val displayNameHtml: String? = null,
        private val revokeRefreshToken: Boolean? = null,
        private val refreshTokenMaxReuse: Int? = null,
        private val accessTokenLifespan: Int? = null,
        private val accessTokenLifespanForImplicitFlow: Int? = null,
        private val ssoSessionIdleTimeout: Int? = null,
        private val ssoSessionMaxLifespan: Int? = null,
        private val ssoSessionIdleTimeoutRememberMe: Int? = null,
        private val ssoSessionMaxLifespanRememberMe: Int? = null,
        private val offlineSessionIdleTimeout: Int? = null,
        private val offlineSessionMaxLifespanEnabled: Boolean? = null,
        private val offlineSessionMaxLifespan: Int? = null,
        private val accessCodeLifespan: Int? = null,
        private val accessCodeLifespanUserAction: Int? = null,
        private val accessCodeLifespanLogin: Int? = null,
        private val actionTokenGeneratedByAdminLifespan: Int? = null,
        private val actionTokenGeneratedByUserLifespan: Int? = null,
        private val enabled: Boolean? = null,
        private val sslRequired: String? = null,
        private val registrationAllowed: Boolean? = null,
        private val registrationEmailAsUsername: Boolean? = null,
        private val rememberMe: Boolean? = null,
        private val verifyEmail: Boolean? = null,
        private val loginWithEmailAllowed: Boolean? = null,
        private val duplicateEmailsAllowed: Boolean? = null,
        private val resetPasswordAllowed: Boolean? = null,
        private val editUsernameAllowed: Boolean? = null,
        private val bruteForceProtected: Boolean? = null,
        private val permanentLockout: Boolean? = null,
        private val maxFailureWaitSeconds: Int? = null,
        private val minimumQuickLoginWaitSeconds: Int? = null,
        private val waitIncrementSeconds: Int? = null,
        private val quickLoginCheckMilliSeconds: Int? = null,
        private val maxDeltaTimeSeconds: Int? = null,
        private val failureFactor: Int? = null,
        private val defaultRoles: List<String>? = null,
        private val requiredCredentials: List<String>? = null,
        private val otpPolicyType: String? = null,
        private val otpPolicyAlgorithm: String? = null,
        private val otpPolicyInitialCounter: Int? = null,
        private val otpPolicyDigits: Int? = null,
        private val otpPolicyLookAheadWindow: Int? = null,
        private val otpPolicyPeriod: Int? = null,
        private val otpSupportedApplications: List<String>? = null,
        private val webAuthnPolicyRpEntityName: String? = null,
        private val webAuthnPolicySignatureAlgorithms: List<String>? = null,
        private val webAuthnPolicyRpId: String? = null,
        private val webAuthnPolicyAttestationConveyancePreference: String? = null,
        private val webAuthnPolicyAuthenticatorAttachment: String? = null,
        private val webAuthnPolicyRequireResidentKey: String? = null,
        private val webAuthnPolicyUserVerificationRequirement: String? = null,
        private val webAuthnPolicyCreateTimeout: Int? = null,
        private val webAuthnPolicyAvoidSameAuthenticatorRegister: Boolean? = null,
        private val webAuthnPolicyAcceptableAaguids: List<String>? = null,
        private val browserSecurityHeaders: Map<String, String>? = null,
        private val smtpServer: Map<String, String>? = null,
        private val eventsEnabled: Boolean? = null,
        private val eventsListeners: List<String>? = null,
        private val enabledEventTypes: List<String>? = null,
        private val adminEventsEnabled: Boolean? = null,
        private val adminEventsDetailsEnabled: Boolean? = null,
        private val internationalizationEnabled: Boolean? = null,
        private val supportedLocales: List<String>? = null,
        private val browserFlow: String? = null,
        private val registrationFlow: String? = null,
        private val directGrantFlow: String? = null,
        private val resetCredentialsFlow: String? = null,
        private val clientAuthenticationFlow: String? = null,
        private val dockerAuthenticationFlow: String? = null,
        private val attributes: Map<String, String>? = null,
        private val userManagedAccessAllowed: Boolean? = null) : Action() {


    lateinit var oldRealm: Realm

    private fun updateRealm() = Realm(id, realmName ?: oldRealm.realm,
            displayName ?: oldRealm.displayName,
            displayNameHtml ?: oldRealm.displayNameHtml,
            oldRealm.notBefore,
            revokeRefreshToken ?: oldRealm.revokeRefreshToken,
            refreshTokenMaxReuse ?: oldRealm.refreshTokenMaxReuse,
            accessTokenLifespan ?: oldRealm.accessTokenLifespan,
            accessTokenLifespanForImplicitFlow ?: oldRealm.accessTokenLifespanForImplicitFlow,
            ssoSessionIdleTimeout ?: oldRealm.ssoSessionIdleTimeout,
            ssoSessionMaxLifespan ?: oldRealm.ssoSessionMaxLifespan,
            ssoSessionIdleTimeoutRememberMe ?: oldRealm.ssoSessionIdleTimeoutRememberMe,
            ssoSessionMaxLifespanRememberMe ?: oldRealm.ssoSessionMaxLifespanRememberMe,
            offlineSessionIdleTimeout ?: oldRealm.offlineSessionIdleTimeout,
            offlineSessionMaxLifespanEnabled ?: oldRealm.offlineSessionMaxLifespanEnabled,
            offlineSessionMaxLifespan ?: oldRealm.offlineSessionMaxLifespan,
            accessCodeLifespan ?: oldRealm.accessCodeLifespan,
            accessCodeLifespanUserAction ?: oldRealm.accessCodeLifespanUserAction,
            accessCodeLifespanLogin ?: oldRealm.accessCodeLifespanLogin,
            actionTokenGeneratedByAdminLifespan ?: oldRealm.actionTokenGeneratedByAdminLifespan,
            actionTokenGeneratedByUserLifespan ?: oldRealm.actionTokenGeneratedByUserLifespan,
            enabled ?: oldRealm.enabled,
            sslRequired ?: oldRealm.sslRequired,
            registrationAllowed ?: oldRealm.registrationAllowed,
            registrationEmailAsUsername ?: oldRealm.registrationEmailAsUsername,
            rememberMe ?: oldRealm.rememberMe,
            verifyEmail ?: oldRealm.verifyEmail,
            loginWithEmailAllowed ?: oldRealm.loginWithEmailAllowed,
            duplicateEmailsAllowed ?: oldRealm.duplicateEmailsAllowed,
            resetPasswordAllowed ?: oldRealm.resetPasswordAllowed,
            editUsernameAllowed ?: oldRealm.editUsernameAllowed,
            bruteForceProtected ?: oldRealm.bruteForceProtected,
            permanentLockout ?: oldRealm.permanentLockout,
            maxFailureWaitSeconds ?: oldRealm.maxFailureWaitSeconds,
            minimumQuickLoginWaitSeconds ?: oldRealm.minimumQuickLoginWaitSeconds,
            waitIncrementSeconds ?: oldRealm.waitIncrementSeconds,
            quickLoginCheckMilliSeconds ?: oldRealm.quickLoginCheckMilliSeconds,
            maxDeltaTimeSeconds ?: oldRealm.maxDeltaTimeSeconds,
            failureFactor ?: oldRealm.failureFactor,
            defaultRoles ?: oldRealm.defaultRoles,
            requiredCredentials ?: oldRealm.requiredCredentials,
            otpPolicyType ?: oldRealm.otpPolicyType,
            otpPolicyAlgorithm ?: oldRealm.otpPolicyAlgorithm,
            otpPolicyInitialCounter ?: oldRealm.otpPolicyInitialCounter,
            otpPolicyDigits ?: oldRealm.otpPolicyDigits,
            otpPolicyLookAheadWindow ?: oldRealm.otpPolicyLookAheadWindow,
            otpPolicyPeriod ?: oldRealm.otpPolicyPeriod,
            otpSupportedApplications ?: oldRealm.otpSupportedApplications,
            webAuthnPolicyRpEntityName ?: oldRealm.webAuthnPolicyRpEntityName,
            webAuthnPolicySignatureAlgorithms ?: oldRealm.webAuthnPolicySignatureAlgorithms,
            webAuthnPolicyRpId ?: oldRealm.webAuthnPolicyRpId,
            webAuthnPolicyAttestationConveyancePreference ?: oldRealm.webAuthnPolicyAttestationConveyancePreference,
            webAuthnPolicyAuthenticatorAttachment ?: oldRealm.webAuthnPolicyAuthenticatorAttachment,
            webAuthnPolicyRequireResidentKey ?: oldRealm.webAuthnPolicyRequireResidentKey,
            webAuthnPolicyUserVerificationRequirement ?: oldRealm.webAuthnPolicyUserVerificationRequirement,
            webAuthnPolicyCreateTimeout ?: oldRealm.webAuthnPolicyCreateTimeout,
            webAuthnPolicyAvoidSameAuthenticatorRegister ?: oldRealm.webAuthnPolicyAvoidSameAuthenticatorRegister,
            webAuthnPolicyAcceptableAaguids ?: oldRealm.webAuthnPolicyAcceptableAaguids,
            browserSecurityHeaders ?: oldRealm.browserSecurityHeaders,
            smtpServer ?: oldRealm.smtpServer,
            eventsEnabled ?: oldRealm.eventsEnabled,
            eventsListeners ?: oldRealm.eventsListeners,
            enabledEventTypes ?: oldRealm.enabledEventTypes,
            adminEventsEnabled ?: oldRealm.adminEventsEnabled,
            adminEventsDetailsEnabled ?: oldRealm.adminEventsDetailsEnabled,
            internationalizationEnabled ?: oldRealm.internationalizationEnabled,
            supportedLocales ?: oldRealm.supportedLocales,
            browserFlow ?: oldRealm.browserFlow,
            registrationFlow ?: oldRealm.registrationFlow,
            directGrantFlow ?: oldRealm.directGrantFlow,
            resetCredentialsFlow ?: oldRealm.resetCredentialsFlow,
            clientAuthenticationFlow ?: oldRealm.clientAuthenticationFlow,
            dockerAuthenticationFlow ?: oldRealm.dockerAuthenticationFlow,
            mergeAttributes(),
            userManagedAccessAllowed ?: oldRealm.userManagedAccessAllowed)

    private fun mergeAttributes(): Map<String, String> {
        val newMap = oldRealm.attributes.toMutableMap()
        attributes?.forEach {
            newMap[it.key] = it.value
        }
        return newMap
    }

    override fun execute() {
        if (!client.realmExistsById(id)) {
            throw MigrationException("Realm with id: $id does not exist!")
        }
        oldRealm = client.realmById(id)
        client.updateRealm(id, updateRealm())
    }

    override fun undo() {
        client.updateRealm(oldRealm.id, oldRealm)
    }

    override fun name() = "UpdateRealm id: $id"

}