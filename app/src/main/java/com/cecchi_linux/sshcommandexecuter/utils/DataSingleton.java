package com.cecchi_linux.sshcommandexecuter.utils;

import com.cecchi_linux.sshcommandexecuter.model.Command;
import com.cecchi_linux.sshcommandexecuter.model.MyConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * Created by Enri on 31/03/2016.
 */
public class DataSingleton {

    private static DataSingleton instance = null;
    private List<MyConnection> connections;

    private DataSingleton(){
        this.connections = new ArrayList<>();
    }

    public static DataSingleton getInstance(){
        if(instance == null){
            instance = new DataSingleton();
        }
        return instance;
    }

    public void addConnection(MyConnection connection){

        for(MyConnection con : this.connections){
            if(con.getConnectionName().equals(connection.getConnectionName())){
                //TODO exception
            }
        }
        this.connections.add(connection);
    }

    public void addCommand(Command command, String connectionName){
        //boolean isPresent = false;

        for(MyConnection con : this.connections){
            if(con.getConnectionName().equals(connectionName)){
                con.addCommand(command);
            }
        }
    }

    public List<MyConnection> getConnections(){
        return this.connections;
    }

    public List<Command> getCommands(String connectionName) {
        for(MyConnection con : this.connections){
            if(con.getConnectionName().equals(connectionName)){
                return con.getCommands();
            }
        }
        //TODO exception
        return null;
    }

    public ArrayList<String> execCommand(String connectionName, String commandName) throws ExecutionException, InterruptedException {
        for(MyConnection con : this.connections){
            if(con.getConnectionName().equals(connectionName)){
                for(Command com : con.getCommands()){
                    if(com.getCommandName().equals(commandName)){
                        ExecutorService esecutore = Executors.newCachedThreadPool();

                        Future<ArrayList<String>> f = esecutore.submit(new ExecThread(con, com));
                        ArrayList<String> result = f.get();

                        esecutore.shutdown();

                        return result;

                    }
                }
            }
        }
        return null;
    }

//    public static ArrayList<String> execSSH(MyConnection con, Command com) {
//        /**
//         * L'oggetto seguente fungerà da contenitore per l'output
//         * del comando ls che eseguiremo via SSH
//         */
//
//        ArrayList<String> ls = new ArrayList<String>();
//        try {
//            //Creo l'oggetto Connection, ed avvio la connessione
//            Connection conn = new Connection(ip);
//            conn.connect();
//
//            //Effettuo l'autenticazione...
//            boolean isAuthenticated = conn.authenticateWithPassword(usr, psw);
//            //...e verifico che sia andata a buon fine
//            if (isAuthenticated == false) {
//                return null;
//            }
//
//            //Creo l'oggetto Session, aprendo di fatto una sessione
//            Session sess = conn.openSession();
//
//            //Eseguo il comando...
//            //sess.execCommand("ls -r " + dir);
//            sess.execCommand();
//
//            //...e ne gestisco l'output, popolando l'ArrayList
//            InputStream stdout = new StreamGobbler(sess.getStdout());
//            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//            while (true) {
//                String line = br.readLine();
//                if (line == null) break;
//                ls.add(line);
//            }
//
//            //Chiudo la sessione..
//            sess.close();
//            //...e la connessione
//            conn.close();
//        } catch (IOException e) {
//            return null;
//        }
//        return ls;
//    }

//    public class ExecThread extends Thread {
//
//        MyConnection connection;
//        Command command;
//
//        public ExecThread(MyConnection connection, Command command){
//
//        }
//
//        public void run() {
//            int n;
//            for(n=0;n<6;n++) {
//                System.out.println("L'indice n è uguale a : " + n);
//            }
//        }
//    }

    private class ExecThread implements Callable<ArrayList<String>> {

        MyConnection connection;
        Command command;

        public ExecThread (MyConnection connection, Command command){
            this.connection = connection;
            this.command = command;
        }

        public ArrayList<String> call()
        {
            ArrayList<String> res = new ArrayList<String>();
            try {
                //Creo l'oggetto Connection, ed avvio la connessione
                Connection conn = new Connection(this.connection.getAddress());
                conn.connect();

                //Effettuo l'autenticazione...
                boolean isAuthenticated = conn.authenticateWithPassword(this.connection.getUserName(), this.connection.getUserPassword());
                //...e verifico che sia andata a buon fine
                if (isAuthenticated == false) {
                    return null;
                }

                //Creo l'oggetto Session, aprendo di fatto una sessione
                Session sess = conn.openSession();

                //Eseguo il comando...
                //sess.execCommand("ls -r " + dir);
                sess.execCommand(this.command.getStrCommand());

                //...e ne gestisco l'output, popolando l'ArrayList
                InputStream stdout = new StreamGobbler(sess.getStdout());
                BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    res.add(line);
                }

                InputStream stderr = new StreamGobbler(sess.getStderr());
                br = new BufferedReader(new InputStreamReader(stderr));
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    res.add(line);
                }

                //Chiudo la sessione..
                sess.close();
                //...e la connessione
                conn.close();
            } catch (IOException e) {
                return null;
            }
            return res;
        }
    }
}
