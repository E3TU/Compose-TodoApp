package com.example.todoapp.ui.todo

import android.R
import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val completed: Boolean = false
)

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAll(): Flow<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo): Long

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

object DataBaseProvider {
    @Volatile
    private var instance: TodoDatabase? = null

    fun get(context: Context): TodoDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo_database"
            ).build().also { instance = it }
        }
    }
}

class TodoRepository(context: Context) {
    private val dao = DataBaseProvider.get(context).todoDao()

    val todos: Flow<List<Todo>> = dao.getAll()

    suspend fun add(id: Long, title: String, description: String, completed: Boolean) =
        dao.insert(Todo(id = id, title = title, description = description, completed = completed))

    suspend fun toggle(todo: Todo) = dao.update(todo.copy(completed = !todo.completed))
    suspend fun delete(todo: Todo) = dao.delete(todo)
}