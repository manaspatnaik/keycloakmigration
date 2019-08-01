package de.klg71.keycloakmigration.changeControl.actions.user

import de.klg71.keycloakmigration.changeControl.actions.Action
import de.klg71.keycloakmigration.changeControl.actions.MigrationException
import de.klg71.keycloakmigration.model.AssignGroup
import de.klg71.keycloakmigration.model.Group
import de.klg71.keycloakmigration.rest.*
import org.apache.commons.codec.digest.DigestUtils

class AssignGroupAction(
        realm:String?=null,
        private val user: String,
        private val group: String) : Action(realm) {

    private val hash = calculateHash()

    private fun calculateHash() =
            StringBuilder().run {
                append(realm)
                append(group)
                append(user)
                toString()
            }.let {
                DigestUtils.sha256Hex(it)
            }!!

    override fun hash() = hash

    override fun execute() {
        if (!client.existsUser(user, realm())) {
            throw MigrationException("User with name: $user does not exist in realm: ${realm()}!")
        }
        if (!client.existsGroup(group, realm())) {
            throw MigrationException("Group with name: $group does not exist in realm: ${realm()}!")
        }

        findGroup().run {
            assignGroup()
        }.let {
            client.assignGroup(it, realm(), client.userUUID(user, realm()), client.groupUUID(group, realm()))
        }
    }

    private fun Group.assignGroup() = AssignGroup(realm(), id, client.userUUID(user, realm()))

    override fun undo() {
        client.revokeGroup(realm(), client.userUUID(user, realm()), client.groupUUID(group, realm()))
    }

    private fun findGroup() = client.groupByName(group, realm())

    override fun name() = "AssignGroup $group to $user"
}