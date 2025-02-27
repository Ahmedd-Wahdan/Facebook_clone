package facebook.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DATA extends root_Data {

    public static void read(){
        read_users();
        readPosts();
        read_interactions();
    }
    public static void writeDataToFile() {
        clearFile("Text Files/Users.txt");
        clearFile("Text Files/Friends.txt");
        clearFile("Text Files/restricted_users.txt");
        clearFile("Text Files/Profile_Photo.txt");
        clearFile("Text Files/posts.txt");
        clearFile("Text Files/interactions.txt");

        writeUsersToFile();
        writeFriendsToFile();
        writeRestrictedUsersToFile();
        writePhotosToFile();
        writePostsToFile();
        writeInteractionsToFile();
    }

    private static void read_users() {

        final String usersFilePath="Text Files/Users.txt";
        final String friendsFilePath="Text Files/Friends.txt";
        final String restrictedUsersFilePath="Text Files/restricted_users.txt";
        final String photosFilePath = "Text Files/Profile_Photo.txt";
        try {
            // Read users
            File usersFile = new File(usersFilePath);
            Scanner usersScanner = new Scanner(usersFile);
            BufferedReader reader = Files.newBufferedReader(Paths.get(usersFilePath), StandardCharsets.UTF_8);
            {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                        User user;
                    if (parts.length == 6) {
                        int id = Integer.parseInt(parts[0]);
                        String email = parts[1];
                        String name = parts[2];
                        String password = parts[3];
                        String gender = parts[4];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(parts[5], formatter);
                        user = new User(id, name, email, gender, date, password, new ArrayList<Integer>(), new ArrayList<Integer>());
                        System.out.println(user.Email);
                        DATA.users.add(user);
                    }
                }
            }








            // Read friends
            File friendsFile = new File(friendsFilePath);
            Scanner friendsScanner = new Scanner(friendsFile);

            while (friendsScanner.hasNextLine()) {
                String line = friendsScanner.nextLine();
                String[] friendData = line.split(":");
                int userId = Integer.parseInt(friendData[0]);

                if (userId <= DATA.users.size()) {
                    User user = DATA.users.get(userId - 1);
                    String[] friends = friendData[1].split(" ");

                    for (String friendId : friends) {
                        int friendUserId = Integer.parseInt(friendId);
                        if (friendUserId <= DATA.users.size()) {
                            user.friends.add(friendUserId);
                        }
                    }
                }
            }

            friendsScanner.close();

            // Read restricted users
            File restrictedUsersFile = new File(restrictedUsersFilePath);
            Scanner restrictedUsersScanner = new Scanner(restrictedUsersFile);

            while (restrictedUsersScanner.hasNextLine()) {
                String line = restrictedUsersScanner.nextLine();
                String[] restrictedUserData = line.split(":");
                int userId = Integer.parseInt(restrictedUserData[0]);

                if (userId <= DATA.users.size()) {
                    User user = DATA.users.get(userId - 1);
                    int restrictedUserId = Integer.parseInt(restrictedUserData[1]);

                    if (restrictedUserId <= DATA.users.size()) {
                        user.restricted_users.add(restrictedUserId);
                    }
                }
            }

            restrictedUsersScanner.close();

            // Read users
            File photosFile = new File(photosFilePath);
            Scanner PhotosScanner = new Scanner(photosFile);

            while (PhotosScanner.hasNextLine()) {
                String line = PhotosScanner.nextLine();
                String[] userData = line.split(" ");

                int id = Integer.parseInt(userData[0]);
                User user = DATA.users.get(id - 1);
                user.profile_photo_path = userData[1];
            }

            PhotosScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private static void readPosts() {

        String filePath = "Text Files/posts.txt";
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");

                int post_id = Integer.parseInt(parts[0]);
                boolean is_public = Boolean.parseBoolean(parts[1]);
                int author_id = Integer.parseInt(parts[2]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(parts[3], formatter);


                // Join the remaining parts to form the content
                StringBuilder contentBuilder = new StringBuilder();
                for (int i = 4; i < parts.length; i++) {
                    contentBuilder.append(parts[i]).append(" ");
                }
                String content = contentBuilder.toString().trim();

                Post postt = new Post(post_id, date, is_public, content, author_id);
                DATA.Posts.add(postt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    private static void read_interactions() {

        try (BufferedReader reader = new BufferedReader(new FileReader("Text Files/interactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[0]);
                int postId = Integer.parseInt(parts[1]);
                DATA.interactionList.add(new interactions(userId, postId));
            }
        } catch (IOException | NumberFormatException exception) {
            exception.getLocalizedMessage();
        }
    }


    private static void writeUsersToFile() {
        String usersFilePath = "Text Files/Users.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFilePath))) {
            for (User user : users) {
                writer.write(user.id + " " + user.getName().replace(' ', '_') + " " + user.password + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFriendsToFile() {
        String friendsFilePath = "Text Files/Friends.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(friendsFilePath))) {
            for (User user : users) {
                int userId = user.id;
                ArrayList<Integer> friends = user.friends;
                writer.write(userId + ":" + String.join(" ", friends.stream().map(String::valueOf).toArray(String[]::new)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeRestrictedUsersToFile() {
        String restrictedUsersFilePath = "Text Files/restricted_users.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(restrictedUsersFilePath))) {
            for (User user : users) {
                int userId = user.id;
                ArrayList<Integer> restrictedUsers = user.restricted_users;
                writer.write(userId + ":" + String.join(" ", restrictedUsers.stream().map(String::valueOf).toArray(String[]::new)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writePhotosToFile() {
        String photosFilePath = "Text Files/Profile_Photo.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(photosFilePath))) {
            for (User user : users) {
                int userId = user.id;
                String profilePhotoPath = user.profile_photo_path;
                writer.write(userId + " " + profilePhotoPath + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writePostsToFile() {
        String postsFilePath = "Text Files/posts.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(postsFilePath))) {
            for (Post post : Posts) {
                writer.write(post.post_id + " " + post.is_public + " " + post.author_id + " " +
                        post.Date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + post.content + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeInteractionsToFile() {
        String interactionsFilePath = "Text Files/interactions.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(interactionsFilePath))) {
            for (interactions interaction : interactionList) {
                writer.write(interaction.getUser_id() + "," + interaction.getPost_id() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void clearFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}