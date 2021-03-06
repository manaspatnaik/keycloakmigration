package de.klg71.keycloakmigration.changeControl.actions.client

import de.klg71.keycloakmigration.changeControl.actions.Action
import de.klg71.keycloakmigration.changeControl.actions.MigrationException
import de.klg71.keycloakmigration.keycloakapi.model.AssignRole
import de.klg71.keycloakmigration.keycloakapi.model.Role
import de.klg71.keycloakmigration.keycloakapi.clientById
import de.klg71.keycloakmigration.keycloakapi.clientRoleByName
import de.klg71.keycloakmigration.keycloakapi.clientUUID
import de.klg71.keycloakmigration.keycloakapi.existsClient
import de.klg71.keycloakmigration.keycloakapi.existsClientRole
import de.klg71.keycloakmigration.keycloakapi.existsRole
import java.util.Objects.isNull

class AssignRoleToClientAction(
        realm: String? = null,
        private val role: String,
        private val clientId: String,
        private val roleClientId: String? = null) : Action(realm) {

    override fun execute() {
        if (!client.existsClient(clientId, realm())) {
            throw MigrationException("Client with name: $clientId does not exist in realm: ${realm()}!")
        }
        if (roleClientId == null) {
            if (!client.existsRole(role, realm())) {
                throw MigrationException("Role with name: $role does not exist in realm: ${realm()}!")
            }
        } else {
            if (!client.existsClientRole(role, realm(), roleClientId)) {
                throw MigrationException(
                        "Role with name: $role in client: $roleClientId does not exist in realm: ${realm()}!")
            }
        }
        val serviceClient = client.clientById(clientId, realm())
        if (!serviceClient.serviceAccountsEnabled) {
            throw MigrationException("Service account not enabled for client: $clientId!")
        }

        val serviceAccountUser = client.clientServiceAccount(serviceClient.id, realm())
        findRole().run {
            assignRole()
        }.let {
            if (roleClientId != null) {
                client.assignClientRoles(listOf(it), realm(), serviceAccountUser.id,
                        client.clientUUID(roleClientId, realm()))
            } else {
                client.assignRealmRoles(listOf(it), realm(), serviceAccountUser.id)
            }
        }
    }

    private fun Role.assignRole() = AssignRole(isNull(client), composite, containerId, id, name)

    override fun undo() {
        val serviceClient = client.clientById(clientId, realm())
        val serviceAccountUser = client.clientServiceAccount(serviceClient.id, realm())
        findRole().run {
            assignRole()
        }.let {
            if (roleClientId != null) {
                client.revokeClientRoles(listOf(it), realm(), serviceAccountUser.id,
                        client.clientUUID(roleClientId, realm()))
            } else {
                client.revokeRealmRoles(listOf(it), realm(), serviceAccountUser.id)
            }
        }
    }

    private fun findRole() = if (roleClientId == null) {
        client.roleByName(role, realm())
    } else {
        client.clientRoleByName(role, roleClientId, realm())
    }

    override fun name() = "AssignRole $role in client: $roleClientId to Client ServiceAccount: $clientId"

}
