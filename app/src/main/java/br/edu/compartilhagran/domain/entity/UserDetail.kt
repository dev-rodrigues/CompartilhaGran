package br.edu.compartilhagran.domain.entity

import com.google.firebase.firestore.DocumentId

class UserDetail(
    @DocumentId
    var id: String ?= null,
    var foreignKey: String ?= null,
    var fullName: String ?= null
)