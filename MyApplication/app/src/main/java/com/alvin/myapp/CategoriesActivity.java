package com.alvin.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static ArrayList<Category> categories;
    CustomCategoryAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        categories = new ArrayList<>();
        populateCategoriesList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHighscores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, QuizActivity.class);
        switch (item.getItemId()) {
            case R.id.quiz_fruits:
                intent.putExtra("position", 0);
                startActivity(intent);
                return true;
            case R.id.quiz_animals:
                intent.putExtra("position", 1);
                startActivity(intent);
                return true;
            case R.id.quiz_food:
                intent.putExtra("position", 2);
                startActivity(intent);
                return true;
            case R.id.quiz_colors:
                intent.putExtra("position", 3);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateCategoriesList() {
        Highscores.open(this);
        Category fruitCategory = new Category("Fruits",
                R.drawable.fruits,
                Highscores.getHighscore(MySQLiteHelper.COLUMN_FRUITS),
                getResources().getColor(R.color.primary_dark),
                R.style.GreenTheme,
                MySQLiteHelper.COLUMN_FRUITS,
                R.drawable.ic_cauhoi);
        Category animalCategory = new Category("Animals",
                R.drawable.animals,
                Highscores.getHighscore(MySQLiteHelper.COLUMN_ANIMALS),
                getResources().getColor(R.color.blue_primary_dark),
                R.style.BlueTheme,
                MySQLiteHelper.COLUMN_ANIMALS,
                R.drawable.ic_cauhoi);
        Category foodCategory = new Category("Food",
                R.drawable.food,
                Highscores.getHighscore(MySQLiteHelper.COLUMN_FOOD),
                getResources().getColor(R.color.pink_primary_dark),
                R.style.PinkTheme,
                MySQLiteHelper.COLUMN_FOOD,
                R.drawable.ic_cauhoi);
        Category colorsCategory = new Category("Colors",
                R.drawable.colors,
                Highscores.getHighscore(MySQLiteHelper.COLUMN_COLORS),
                getResources().getColor(R.color.purple_primary_dark),
                R.style.PurpleTheme,
                MySQLiteHelper.COLUMN_COLORS,
                R.drawable.ic_cauhoi);
        Highscores.close();

        fruitCategory.addThing(new Thing(R.drawable.saurieng, R.raw.durian, "Durian - Quả sầu riêng"));
        fruitCategory.addThing(new Thing(R.drawable.quanhan, R.raw.logan, "Logan - Quả nhãn"));
        fruitCategory.addThing(new Thing(R.drawable.apple, R.raw.apple, "Apple - Quả táo"));
        fruitCategory.addThing(new Thing(R.drawable.orange, R.raw.orange, "Orange - Quả cam"));
        fruitCategory.addThing(new Thing(R.drawable.banana, R.raw.banana, "Banana - Quả chuối"));
        fruitCategory.addThing(new Thing(R.drawable.coconut, R.raw.coconut, "Coconut - Quả dừa "));
        fruitCategory.addThing(new Thing(R.drawable.kiwi, R.raw.kiwi, "Kiwi - Quả kiwi"));
        fruitCategory.addThing(new Thing(R.drawable.lemon, R.raw.lemon, "Lemon - Quả chanh"));
        fruitCategory.addThing(new Thing(R.drawable.peach, R.raw.peach, "Peach - Quả đào"));
        fruitCategory.addThing(new Thing(R.drawable.pear, R.raw.pear, "Pear - Quả lê"));
        fruitCategory.addThing(new Thing(R.drawable.persimmon, R.raw.persimmon, "Persimmon - Quả hồng"));
        fruitCategory.addThing(new Thing(R.drawable.pineapple, R.raw.pineapple, "Pineapple - Quả dứa"));
        fruitCategory.addThing(new Thing(R.drawable.plum, R.raw.plum, "Plum - Quả mận"));
        fruitCategory.addThing(new Thing(R.drawable.raspberry, R.raw.raspberry, "Raspberry - Quả dâu tằm"));
        fruitCategory.addThing(new Thing(R.drawable.strawberry, R.raw.strawberry, "Strawberry - Quả dâu tây"));
        fruitCategory.addThing(new Thing(R.drawable.watermelon, R.raw.watermelon, "Watermelon - Quả dưa hấu"));
        fruitCategory.addThing(new Thing(R.drawable.mango, R.raw.mango, "Mango - Quả xoài"));

        animalCategory.addThing(new Thing(R.drawable.dog, R.raw.dog, "Dog - Con chó", R.raw.smalldog));
        animalCategory.addThing(new Thing(R.drawable.bear, R.raw.bear, "Bear - Con gấu"));
        animalCategory.addThing(new Thing(R.drawable.wolf, R.raw.wolf, "Wolf - Con sói"));
        animalCategory.addThing(new Thing(R.drawable.dolphin, R.raw.dolphin, "Dolphin - Cá heo"));
        animalCategory.addThing(new Thing(R.drawable.duck, R.raw.duck, "Duck - Vịt"));
        animalCategory.addThing(new Thing(R.drawable.leopard, R.raw.leopard, "Leopard - Báo"));
        animalCategory.addThing(new Thing(R.drawable.lion, R.raw.lion, "Lion - Sư tử"));
        animalCategory.addThing(new Thing(R.drawable.monkey, R.raw.monkey, "Monkey - Con khỉ"));
        animalCategory.addThing(new Thing(R.drawable.penguin, R.raw.penguin, "Penguin - Chim cánh cụt"));
        animalCategory.addThing(new Thing(R.drawable.rooster, R.raw.rooster, "Rooster - Gà Trống"));
        animalCategory.addThing(new Thing(R.drawable.sheep, R.raw.sheep, "Sheep - Con cừu"));
        animalCategory.addThing(new Thing(R.drawable.snake, R.raw.snake, "Snake - Con Rắn"));
        animalCategory.addThing(new Thing(R.drawable.tiger, R.raw.tiger, "Tiger - Hổ"));
        animalCategory.addThing(new Thing(R.drawable.fox, R.raw.fox, "Fox -Cáo"));
        animalCategory.addThing(new Thing(R.drawable.camel, R.raw.camel, "Camel - Lạc đà"));

        foodCategory.addThing(new Thing(R.drawable.dauphu, R.raw.tofu, "Tofu - Đậu phụ"));
        foodCategory.addThing(new Thing(R.drawable.chocolate, R.raw.chocolate, "Chocolate - Socola"));
        foodCategory.addThing(new Thing(R.drawable.bread, R.raw.bread, "Bread - Bánh mì"));
        foodCategory.addThing(new Thing(R.drawable.burger, R.raw.burger, "Burger - Bánh mì kẹp"));
        foodCategory.addThing(new Thing(R.drawable.cheese, R.raw.cheese, "Cheese - Phô mai"));
        foodCategory.addThing(new Thing(R.drawable.coffee, R.raw.coffee, "Coffee - Cà phê"));
        foodCategory.addThing(new Thing(R.drawable.egg, R.raw.egg, "Egg -Trứng"));
        foodCategory.addThing(new Thing(R.drawable.honey, R.raw.honey, "Honey - Mật ong"));
        foodCategory.addThing(new Thing(R.drawable.meat, R.raw.meat, "Meat -Thịt lợn"));
        foodCategory.addThing(new Thing(R.drawable.pizza, R.raw.pizza, "Pizza - Bánh pizza"));
        foodCategory.addThing(new Thing(R.drawable.xucxich, R.raw.sausage, "Sausage - Xúc xích"));
        foodCategory.addThing(new Thing(R.drawable.water, R.raw.water, "Water - Nước"));

        colorsCategory.addThing(new Thing(R.drawable.blue, R.raw.blue, "Blue - Màu xanh"));
        colorsCategory.addThing(new Thing(R.drawable.pink, R.raw.pink, "Pink - Màu hồng"));
        colorsCategory.addThing(new Thing(R.drawable.green, R.raw.green, "Green - Xanh lá"));
        colorsCategory.addThing(new Thing(R.drawable.orange_color, R.raw.orange, "Orange -Màu cam"));
        colorsCategory.addThing(new Thing(R.drawable.purple, R.raw.purple, "Purple -Màu tím"));
        colorsCategory.addThing(new Thing(R.drawable.red, R.raw.red, "Red - Màu đỏ"));
        colorsCategory.addThing(new Thing(R.drawable.yellow, R.raw.yellow, "Yellow - Màu vàng"));
        colorsCategory.addThing(new Thing(R.drawable.brown, R.raw.brown, "Brown - Màu nâu"));
        colorsCategory.addThing(new Thing(R.drawable.gray, R.raw.gray, "Gray - Màu xám"));
        colorsCategory.addThing(new Thing(R.drawable.white, R.raw.white, "White - Màu trắng"));
        colorsCategory.addThing(new Thing(R.drawable.black, R.raw.black, "Black - Màu đen"));
        categories.add(fruitCategory);
        categories.add(animalCategory);
        categories.add(foodCategory);
        categories.add(colorsCategory);

        adapter = new CustomCategoryAdapter(this, categories);
        listView = (ListView) findViewById(R.id.listViewCards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void updateHighscores() {
        Highscores.open(this);
        for (Category c : categories) {
            c.updateHighscore();
        }
        Highscores.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, com.alvin.myapp.ThingsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
