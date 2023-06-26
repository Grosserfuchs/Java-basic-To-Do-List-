import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
            Scanner scanner = new Scanner(System.in);
            List<ToDoList> TDL = new ArrayList<ToDoList>();
            List<ToDo> TD = new ArrayList<ToDo>();

            ToDoManager manager = new ToDoManager();

            int selectedList = -1;

            try{
                while (true) {
                    TDL.clear();
                    ResultSet result = manager.ExecuteQuery("SELECT * FROM titles");
                    while (result.next()) {
                        int id = result.getInt("id");
                        String header = result.getString("title");
                        TDL.add(new ToDoList(id, header));
                    }

                    clearConsole();

                    if (selectedList == -1) {
                        System.out.println();
                        System.out.println("Lütfen bir komut girin.");
                        System.out.println();
                        System.out.println("Bir liste oluşturmak için:");
                        System.out.println("     oluştur <isim>");
                        System.out.println("Bir listeyi incelemek için:");
                        System.out.println("     incele <id>");
                        System.out.println("Bir listeyi silmek için:");
                        System.out.println("     sil <id>");
                        System.out.println("Bir listenin ismini değiştirmek için:");
                        System.out.println("     değiştir <id> <yeni isim>");
                        System.out.println();
                        System.out.println("Listeler:");

                        for (ToDoList i : TDL) {
                            System.out.println("  " + i.id + " - " + i.header);
                        }

                        System.out.println();
                        System.out.print(" > ");

                        List<String> commandArgs = new ArrayList<String>(Arrays.asList(scanner.nextLine().toLowerCase().split(" ")));

                        switch (commandArgs.get(0)) {
                            case "oluştur":
                                if (commandArgs.size() < 2) {
                                    System.out.println("Hatalı giriş.");
                                    break;
                                }

                                commandArgs.remove(0);
                                String name = String.join(" ", commandArgs);

                                manager.Execute("INSERT INTO titles (title) VALUES ('" + name + "')");
                                break;
                            case "incele":
                                if (commandArgs.size() < 2) {
                                    System.out.println("Hatalı giriş.");
                                    break;
                                }

                                selectedList = Integer.parseInt(commandArgs.get(1));

                                break;
                            case "sil":
                                if (commandArgs.size() < 2) {
                                    System.out.println("Hatalı giriş.");
                                    break;
                                }

                                manager.Execute("DELETE FROM titles WHERE id= '" + commandArgs.get(1) + "'");
                                manager.Execute("DELETE FROM todo WHERE title_id= '" + commandArgs.get(1) + "'");
                                break;
                            case "değiştir":
                                if (commandArgs.size() < 2) {
                                    System.out.println("Hatalı giriş.");
                                    break;
                                }

                                commandArgs.remove(0);
                                String id = commandArgs.get(0);
                                commandArgs.remove(0);
                                String update = String.join(" ", commandArgs);

                                manager.Execute("UPDATE titles SET title = '" + update + "' WHERE id=  '" + id + "'");
                                break;
                            default:
                                break;
                        }
                        continue;
                    }

                    clearConsole();

                    TD.clear();
                    ResultSet taskList = manager.ExecuteQuery("SELECT taskid, task FROM todo WHERE title_id = '" + selectedList + "'");
                    while (taskList.next()) {
                        int taskId = taskList.getInt("taskid");
                        String task = taskList.getString("task");
                        TD.add(new ToDo(taskId, task));
                    }

                    System.out.println();
                    System.out.println("Lütfen bir komut girin.");
                    System.out.println();
                    System.out.println("Listeye madde eklemek için:");
                    System.out.println("     oluştur <madde>");
                    System.out.println("Geri dönmek için:");
                    System.out.println("     geri");
                    System.out.println("Maddeyi silmek için:");
                    System.out.println("     sil <id>");
                    System.out.println("Maddeyi düzenlemek için:");
                    System.out.println("     düzenle <id> <yeni içerik>");
                    System.out.println();
                    int finalSelectedList = selectedList;
                    System.out.println(TDL.stream().filter(e -> e.id == finalSelectedList).findFirst().orElse(null).header + ":");

                    for (ToDo i : TD) {
                        System.out.println("  " + i.taskId + " - " + i.task);
                    }

                    System.out.println();
                    System.out.print(" > ");

                    List<String> commandArgs = new ArrayList<String>(Arrays.asList(scanner.nextLine().toLowerCase().split(" ")));

                    switch (commandArgs.get(0)) {
                        case "oluştur":
                            if (commandArgs.size() < 2) {
                                System.out.println("Hatalı giriş.");
                                break;
                            }

                            commandArgs.remove(0);
                            String name = String.join(" ", commandArgs);

                            manager.Execute("INSERT INTO todo (task, title_id) VALUES ('" + name + "','" + selectedList + "')");
                            break;
                        case "sil":
                            if (commandArgs.size() < 2) {
                                System.out.println("Hatalı giriş.");
                                break;
                            }

                            manager.Execute("DELETE FROM todo WHERE taskid= '" + commandArgs.get(1) + "'");
                            break;
                        case "düzenle":
                            if (commandArgs.size() < 2) {
                                System.out.println("Hatalı giriş.");
                                break;
                            }

                            commandArgs.remove(0);
                            String id = commandArgs.get(0);
                            commandArgs.remove(0);
                            String update = String.join(" ", commandArgs);

                            manager.Execute("UPDATE todo SET task = '" + update + "' WHERE taskid=  '" + id + "'");
                            break;
                        case "geri":
                            selectedList = -1;
                            break;
                        default:
                            break;
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }


        }

    public static class ToDoManager {
        Connection connection;
        Statement statement;
        public ToDoManager() throws SQLException {
            Connect();
        }

        public void Connect() throws SQLException {
            connection = DriverManager.getConnection(Settings.CONNECTION_URL, Settings.USERNAME, Settings.PASSWORD);
            statement = connection.createStatement();
        }

        public boolean Execute(String text) throws SQLException {
            return statement.execute(text);
        }

        public ResultSet ExecuteQuery(String text) throws SQLException {
            return statement.executeQuery(text);
        }
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class ToDoList {
        public int id;
        public String header;
        public ToDoList(int id, String header){
            this.id = id;
            this.header = header;
        }
    }

    public static class ToDo {
        public int taskId;
        public String task;

        public ToDo(int taskId, String task){
            this.taskId = taskId;
            this.task = task;
        }
    }

    public static class Settings {
        public static String CONNECTION_URL = "jdbc:postgresql://localhost:5432/tdlist";
        public static String USERNAME = "postgres";
        public static String PASSWORD = "123";

    }
}
