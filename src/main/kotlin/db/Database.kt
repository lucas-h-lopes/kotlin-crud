package db

import io.github.cdimascio.dotenv.dotenv
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

abstract class Database {

    companion object {
        private val dotenv = dotenv()

        fun getConnection(): Connection {
            return DriverManager.getConnection(dotenv["URL"], dotenv["USER"], dotenv["PASSWORD"])
        }

        fun closeConnection(connection: Connection) {
            connection.close()
        }

        fun closeStatement(statement: Statement) {
            statement.close()
        }

        fun createUserTable() {
            val statement: Statement = getConnection().createStatement()
            statement.execute(
                "create table if not exists " + dotenv["DBNAME"] + ".usuarios" +
                        "(id int primary key auto_increment, nome varchar(100) not null, email varchar(200) unique not null)"
            )
        }

        fun createPhoneTable() {
            val statement: Statement = getConnection().createStatement()
            statement.execute(
                "create table if not exists " + dotenv["DBNAME"] + ".telefones" +
                        "(id int primary key auto_increment, ddd char(2) not null, numero char(9) not null, user_id int, foreign key (user_id) references " + dotenv["DBNAME"] +
                        ".usuarios(id))"
            )
        }
    }
}