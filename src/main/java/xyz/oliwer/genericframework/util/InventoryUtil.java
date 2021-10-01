package xyz.oliwer.genericframework.util;

import static java.lang.Math.ceil;

/**
 * This class represents util functionality for minecraft inventories (not java specific).
 */
public final class InventoryUtil {
    /** No instantiation needed. **/
    private InventoryUtil() {
        throw new RuntimeException("Cannot instantiate InventoryUtil");
    }

    /**
     * Get whether an index is a border slot of an inventory or not.
     *
     * @param size {@link Integer} the size of said inventory to check.
     * @param index {@link Integer} said index to perform logic on.
     * @return {@link Boolean} whether the index is a border slot.
     */
    public static boolean isBorderSlot(int size, int index) {
        return index >= 0 && index <= 8 || index >= size - 8 || index % 9 == 0 || index == (row(index) * 9) - 1;
    }

    /**
     * Fetch row by index from inventory.
     *
     * @param index {@link Integer} the index to fetch row by.
     * @return {@link Integer}
     */
    public static int row(int index) {
        final int row = (int) ceil((ceil((double) index / 9) * 9) / 9);
        return row % 9 == 0 ? row + 1 : row;
    }
}