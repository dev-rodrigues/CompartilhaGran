package br.edu.compartilhagran.infrastructure.dao

import com.google.android.gms.tasks.Task

// E -> ENTITY
// K -> KEY
interface DAO<E, K> {
    fun store(entity: E): Task<Void>;
    fun destroy(key: K): Task<Void>;
    fun update(entity: E, key: K): Task<Void>;
}