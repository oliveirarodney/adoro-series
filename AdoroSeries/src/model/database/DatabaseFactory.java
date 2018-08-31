package model.database;

public class DatabaseFactory {
    
    public static DatabaseSQLServer getDatabase(String nome){
        if(nome.equals("sqlserver")){
            return new DatabaseSQLServer();
        }
        return null;
    }
}
