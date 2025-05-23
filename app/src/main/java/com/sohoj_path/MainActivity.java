package com.sohoj_path;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends BaseActivity {
    FirebaseAuth auth;
    //Button button;
    TextView textView;
    NavigationView navigationView;
    FirebaseUser user;
    public DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_drawer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        textView = headerView.findViewById(R.id.user_details);
        //Button button = findViewById(R.id.logout);
        //textView = findViewById(R.id.user_details);
        bottomNavigationView = findViewById(R.id.bottomnavView);
        drawerLayout = findViewById(R.id.main_drawer);

//        navigationView.setNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.logout) {
//                Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), login.class));
//                finish();
//                return true;
//            }
//            return false;
//        });
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

                // Close drawer first
                drawerLayout.closeDrawer(GravityCompat.END);

                // Then sign out
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;

            }else if (item.getItemId() == R.id.about) {
                drawerLayout.closeDrawer(GravityCompat.END); // Close drawer

                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("About Sohoj Path")
                        .setMessage("This app provides vital accessibility info for wheelchair users.\n\nDeveloped by Shazzatul Islam Anam.")
                        .setPositiveButton("OK", null)
                        .show();

                return true;
            }

            else if (item.getItemId() == R.id.change_password) {
                drawerLayout.closeDrawer(GravityCompat.END);


                View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
                TextInputEditText newPasswordInput = dialogView.findViewById(R.id.new_password_input);

                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setView(dialogView)
                        .setPositiveButton("Change", (dialog, which) -> {
                            String newPassword = newPasswordInput.getText().toString().trim();
                            if (newPassword.length() < 6) {
                                Toast.makeText(MainActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            new android.app.AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Confirm Password Change")
                                    .setMessage("Are you sure you want to change your password?")
                                    .setPositiveButton("Yes", (cDialog, cWhich) -> {
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "Password change failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    })
                                    .setNegativeButton("No", null)
                                    .show();

                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }

            else if (item.getItemId() == R.id.clear_cred) {
                drawerLayout.closeDrawer(GravityCompat.END);
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Clear Saved Credentials")
                        .setMessage("Are you sure you want to remove saved login info for this account?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String email = user.getEmail();
                                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                String savedEmail = prefs.getString("saved_email", "");
                                if (email != null && email.equals(savedEmail)) {
                                    prefs.edit().remove("saved_email").remove("saved_password").apply();
                                    Toast.makeText(MainActivity.this, "Credentials cleared successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No saved credentials found for this user", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "No user is logged in", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            }

            else if (item.getItemId() == R.id.del_account) {
                drawerLayout.closeDrawer(GravityCompat.END);
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to permanently delete your account? This action cannot be undone.")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {
                                currentUser.delete()
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();

                                                getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();

                                                Intent intent = new Intent(getApplicationContext(), login.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Account deletion failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(MainActivity.this, "No user is logged in", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }


            return false;
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.navhome)
                {
                    loadFragment(new HomeFragment(),false);
                }
                else if(itemId == R.id.navadd) {
                    loadFragment(new AddFragment(),false);
                }
                else if(itemId == R.id.navprofile){
//                    loadFragment(new ProfileFragment(),false);
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                return  true;
            }
        });
        loadFragment(new HomeFragment(),true);

//        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
//            if (item.getItemId() == R.id.navhome) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });

        user = auth.getCurrentUser();
        if (user == null)
        {
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), login.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });

    }
    public void  loadFragment(Fragment fragment, boolean isAppInitialized)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized)
        {
            fragmentTransaction.add(R.id.framelayout, fragment);
        }
        else{
            fragmentTransaction.replace(R.id.framelayout, fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText || v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
