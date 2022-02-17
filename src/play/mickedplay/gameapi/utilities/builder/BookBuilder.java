package play.mickedplay.gameapi.utilities.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;

public class BookBuilder {
    private String title, author;
    private String[] pages, lore;

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder addPages(String... pages) {
        this.pages = pages;
        return this;
    }

    public BookBuilder setLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) itemStack.getItemMeta();
        if (author != null) itemMeta.setAuthor(author);
        if (title != null) itemMeta.setTitle(title);
        if (pages != null) itemMeta.addPage(pages);
        if (lore != null) itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}