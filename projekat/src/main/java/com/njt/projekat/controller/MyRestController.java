package com.njt.projekat.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.njt.projekat.entity.*;
import com.njt.projekat.entity.security.Role;
import com.njt.projekat.entity.security.UserRole;
import com.njt.projekat.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MyRestController {

    private UserService userService;
    private RoleService roleService;
    private ArtistService artistService;
    private RecordLabelService recordLabelService;
    private GenreService genreService;
    private VinylService vinylService;
    private SongService songService;
    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Artist artist = null;
    private RecordLabel recordLabel = null;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService, ArtistService artistService, RecordLabelService recordLabelService,
                            GenreService genreService, VinylService vinylService, SongService songService, OrderService orderService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.artistService = artistService;
        this.recordLabelService = recordLabelService;
        this.genreService = genreService;
        this.vinylService = vinylService;
        this.songService = songService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/admin/find-create-artist")
    public String findOrCreateArtist(@RequestParam("artistName") String stageName) {
        artist = artistService.findByStageName(stageName);
        if (artist != null) {
            return "exists";
        } else {
            artist = new Artist(stageName);
            artistService.save(artist);
            return "saved";
        }
    }

    @PostMapping("/admin/create-record-label")
    public @ResponseBody
    List<RecordLabel> createRecordLabel(@RequestBody RecordLabel recordLabel) {
        List<RecordLabel> existingRLs = recordLabelService.findAll();
        if (!existingRLs.contains(recordLabel)) {
            recordLabelService.save(recordLabel);
            return existingRLs = recordLabelService.findAll();
        }
        return new ArrayList<>();
    }

    @PostMapping("/admin/create-genre")
    public List<Genre> createGenre(@RequestParam("genreName") String genreName, ModelAndView modelAndView) {
        Genre genre = new Genre(genreName.trim());
        List<Genre> genres = genreService.findAll();
        if (!genres.contains(genre)) {
            genreService.save(genre);
            genres = genreService.findAll();
            return genres;
        }
        return new ArrayList<>();
    }

    @PostMapping(value = "/admin/save-vinyl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveVinyl(@RequestParam("vinylData") String vinylJSON, @RequestParam("file") MultipartFile file)
            throws IOException {
        Vinyl vinyl = objectMapper.readValue(vinylJSON, Vinyl.class);

        JSONObject o = new JSONObject(vinylJSON);
        if (!file.isEmpty()) {
            vinyl.setImgUrl(saveFile(file));
        }
        if (artist == null) {
            String stageName = o.getJSONObject("artist").getString("stageName");
            artist = artistService.findByStageName(stageName);
        }
        vinyl.setArtist(artist);
        System.out.println("THE ARTIST IM SEARCHING FOR: " + artist.getStageName());
        if (vinylService.checkIfExists(vinyl.getVinylName(), artist.getStageName()) != null){
            return "vinylExists";
        }
        JSONObject rlJSON = o.getJSONObject("recordLabel");
        String name = rlJSON.getString("name").trim();
        String year = rlJSON.getString("year").trim();
        Gson gson = new Gson();
        JSONArray gJ = o.getJSONArray("genres");
        List<Genre> gs = gson.fromJson(gJ.toString(), new TypeToken<List<Genre>>() {
        }.getType());
        Genre g;
        vinyl.getGenres().clear();
        for (Genre genre : gs) {
            g = genreService.findByName(genre.getName());
            if (g == null) {
                return "fail";
            } else {
                vinyl.getGenres().add(g);
            }
        }
        vinyl.getSongs().forEach(song -> song.setVinyl(vinyl));
        recordLabel = recordLabelService.findOneByNameAndYear(name, year);
        vinyl.setRecordLabel(recordLabel);
        Vinyl v = vinylService.save(vinyl);
        if (v != null) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/admin/update")
    public String updateVinyl(@RequestParam("editedVinylJSON") String vinylJSON, @RequestParam("file") MultipartFile file) throws IOException {
        Vinyl vinyl = objectMapper.readValue(vinylJSON, Vinyl.class);
        if (!file.isEmpty()) {
            vinyl.setImgUrl(saveFile(file));
        }
        JSONObject o = new JSONObject(vinylJSON);
        if (artist == null) {
            String stageName = o.getJSONObject("artist").getString("stageName");
            artist = artistService.findByStageName(stageName);
        }
        vinyl.setArtist(artist);
        JSONObject rlJSON = o.getJSONObject("recordLabel");
        String name = rlJSON.getString("name").trim();
        String year = rlJSON.getString("year").trim();
        Gson gson = new Gson();
        JSONArray gJ = o.getJSONArray("genres");
        List<Genre> gs = gson.fromJson(gJ.toString(), new TypeToken<List<Genre>>() {}.getType());
        Genre g;
        vinyl.getGenres().clear();
        for (Genre genre : gs) {
            g = genreService.findByName(genre.getName());
            if (g == null) {
                return "fail";
            } else {
                vinyl.getGenres().add(g);
            }
        }
        recordLabel = recordLabelService.findOneByNameAndYear(name, year);
        vinyl.setRecordLabel(recordLabel);
        vinyl.getSongs().forEach(song -> song.setVinyl(vinyl));
        Vinyl v = vinylService.save(vinyl);
        if (v != null) {
            return "success";
        }
        return "fail";
    }

    private String getWorkingPath(String path) {
        String[] parts = path.split(Pattern.quote(File.separator));
        // need parts[4] - parts[6]
        return "/" + parts[4] + "/" + parts[5] + "/" + parts[6];
    }

    private String saveFile(MultipartFile file) throws IOException {
        File convertFile = new File("src/main/resources/static/images/vinyl/" + file.getOriginalFilename());
        String path = convertFile.getPath();
        convertFile.createNewFile();
        FileOutputStream out = new FileOutputStream(convertFile);
        out.write(file.getBytes());
        out.close();
        return getWorkingPath(path);
    }


    @GetMapping("/admin/remove")
    public String redirectAdminToCatalogue(@RequestParam("id") int id, Model model) {
        vinylService.deleteById(id);
        model.addAttribute("vinylDeleted", true);
        return "success";
    }

    @GetMapping("/admin/remove-order")
    public String removeOrder(@RequestParam("id") int id, Model model) {
        System.out.println("removing order with id: " + id);
        orderService.deleteById(id);
        return "success";
    }

    @GetMapping("/cart/remove-item")
    private String removeItemFromCart(@RequestParam("id") int id) {
        shoppingCartService.removeCartItemById(id);
        return "success";
    }

    @GetMapping("/admin/remove-user")
    public String removeUser(@RequestParam("id") int id) {
        User user = userService.findById(id);
        List<Order> orders = orderService.findByUser(user);
        if (!orders.isEmpty()) {
            return "fail";
        }
        userService.deleteById(id);
        return "success";
    }

    @PostMapping("/decrease-cart-qty")
    private String decreaseCartItemQty(@RequestBody String data) {
        JSONObject jsonObject = new JSONObject(data);
        int cartItemId = jsonObject.getInt("cartItemId");
        int newQuantity = jsonObject.getInt("newQuantity");
        shoppingCartService.updateCartItemQty(cartItemId, newQuantity);
        CartItem cartItem = shoppingCartService.findCartItemById(cartItemId);
        Vinyl vinyl = cartItem.getVinyl();
        vinyl.increaseStock(1);
        vinylService.save(vinyl);
        return "success";
    }

    @PostMapping("/increase-cart-qty")
    private String increaseCartItemQty(@RequestBody String data) {
        JSONObject jsonObject = new JSONObject(data);
        int cartItemId = jsonObject.getInt("cartItemId");
        int newQuantity = jsonObject.getInt("newQuantity");
        CartItem cartItem = shoppingCartService.findCartItemById(cartItemId);
        Vinyl vinyl = cartItem.getVinyl();
        if (newQuantity > vinyl.getStock()) {
            return "fail";
        }
        shoppingCartService.updateCartItemQty(cartItemId, newQuantity);
        vinyl.decreaseStock(1);
        vinylService.save(vinyl);
        return "success";
    }

    @PostMapping("/admin/edit-user")
    public String editUser(@RequestBody String data) {
        JSONObject jsonObject = new JSONObject(data);
        int id = Integer.parseInt(jsonObject.getString("id"));
        User user = userService.findById(id);
        Gson gson = new Gson();
        JSONArray jur = jsonObject.getJSONArray("userRoles");
        List<Role> roles = gson.fromJson(jur.toString(), new TypeToken<List<Role>>() {}.getType());
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole;
        for (Role r : roles) {
            r = roleService.findByName(r.getName());
            userRole = new UserRole(user, r);
            userRoles.add(userRole);
        }
        if (userRoles == user.getUserRoles()) {
            return "no change";
        }
        userService.deleteUserRoles(user);
        user.setUserRoles(userRoles);
        userService.save(user);
        return "success";
    }
}
