package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
     * This app displays an order form to order coffee.
     */
    public class MainActivity extends AppCompatActivity {
        int quantity = 2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }

        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder(View view) {
            EditText nameField = (EditText) findViewById(R.id.name_field);
            String name = nameField.getText().toString();

            CheckBox whippedCreamCheckBox = findViewById(R.id.notify_me_checkbox);
       boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
Log.i("MainActivity", "Has whipped cream: " + hasWhippedCream);
            CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckBox.isChecked();
            Log.i("MainActivity", "Has chocolate: " + hasChocolate);

            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java coffee order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

}
        /**
         *
         * @param price of order
         * @return price message.
         */
        private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
            String priceMessage = getString(R.string.client_name) + " " + name;
            priceMessage += "\n" + getString(R.string.Chocolate) + " " + addChocolate;
            priceMessage += "\n" + getString(R.string.whipped_cream) + " " + addWhippedCream;
            priceMessage += "\n" + getString(R.string.order_quantity) + " " + quantity;
            priceMessage += "\n" + getString(R.string.total) + " " + "£" + price;
            priceMessage += "\n" + getString(R.string.thanks);
            return priceMessage;
        }
        /**
         * Calculates the price of the order.
         *
         */
        private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
           int totalSpend = 3;
           if (addWhippedCream) {
               totalSpend = totalSpend + 1;
           }
           if (addChocolate) {
               totalSpend = totalSpend + 2;
           }
            return quantity * totalSpend;
        }

        public void increment(View view) {
            if(quantity ==10) {
                Toast.makeText(this, "Only 10 coffees allowed per order", Toast.LENGTH_SHORT).show();
                return;
            }

            quantity = quantity + 1;
            displayQuantity(quantity);
        }

        public void decrement(View view) {
            if(quantity == 1) {
                Toast.makeText(this, "Minimum order is 1 coffee", Toast.LENGTH_SHORT).show();
                return;
            }

            quantity = quantity - 1;
            displayQuantity(quantity);
        }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int coffeeQuantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + coffeeQuantity);
    }
        /**
         * This method displays the given text on the screen.
         */


}
