package com.example.testapi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.adapter.UserAdapter;
import com.example.testapi.handle.Item_HoaDon_handle;
import com.example.testapi.models.HoaDon;
import com.example.testapi.models.Response;
import com.example.testapi.services.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<HoaDon> hoaDonList;
    private HttpRequest httpRequest;
    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddUser = findViewById(R.id.btnAddUser);
        // Khởi tạo HttpRequest
        httpRequest = new HttpRequest();

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách user và adapter
        hoaDonList = new ArrayList<>();
        userAdapter = new UserAdapter(this, (ArrayList<HoaDon>) hoaDonList, new Item_HoaDon_handle() {
            @Override
            public void onEdit(int position) {
                Log.d("apiSua", "vi trí: " + position);
                showDialog(hoaDonList.get(position), position);

            }
            @Override
            public void onDelete(int position) {
                Log.d("apiXoa", "vị trí: " + position);
                deleteUser(hoaDonList.get(position).getId(), position);
            }
        });
        recyclerView.setAdapter(userAdapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("apiThem", "ok them ");
                showDialog(null, -1);
            }
        });
        // Gọi API để lấy danh sách user
        fetchUserList();
    }

    private void fetchUserList() {
        // Gọi API bằng HttpRequest
        Call<Response<ArrayList<HoaDon>>> call = httpRequest.callApi().getListUser();
        call.enqueue(new Callback<Response<ArrayList<HoaDon>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<HoaDon>>> call,
                                   retrofit2.Response<Response<ArrayList<HoaDon>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<ArrayList<HoaDon>> apiResponse = response.body();

                    Log.d("API_RESPONSE", "dữ liệu: " + apiResponse.getData().toString());
                    if (apiResponse.getStatus() == 200) {
                        hoaDonList.clear();
                        hoaDonList.addAll(apiResponse.getData());
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi: " + apiResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<HoaDon>>> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e("API_ERROR", "Lỗi kết nối: " + errorMessage, t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(HoaDon hoadon, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_user, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText edMaHD = dialogView.findViewById(R.id.etMaHD);
        EditText edTenHD = dialogView.findViewById(R.id.etTenHD);
        EditText edTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etSLuong = dialogView.findViewById(R.id.etSLuong);
        EditText etGia = dialogView.findViewById(R.id.etGia);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // dữ liệu nếu là sửa
        if (hoadon != null) {
            edTenHD.setText(hoadon.getTenHD() != null ? hoadon.getTenHD() : "");
            edTenSP.setText(hoadon.getTenSP() != null ? hoadon.getTenSP() : "");
            etSLuong.setText(hoadon.getSLuong() != null ? hoadon.getSLuong() : "");
            etGia.setText(hoadon.getGia() != null ? hoadon.getGia() : "");
            edMaHD.setText(hoadon.getMaHD() != null ? hoadon.getMaHD() : "");
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maHD = edMaHD.getText().toString().trim();
                String tenHD = edTenHD.getText().toString().trim();
                String tenSP = edTenSP.getText().toString().trim();
                String SLuong = etSLuong.getText().toString().trim();
                String gia = etGia.getText().toString().trim();


                // đối tượng user
                HoaDon newHoaDon = new HoaDon();
                newHoaDon.setMaHD(maHD);
                newHoaDon.setTenHD(tenHD);
                newHoaDon.setTenSP(tenSP);
                newHoaDon.setSLuong(SLuong);
                newHoaDon.setGia(gia);


                if (hoadon == null) {
                    // Thêm user mới
                    addUser(newHoaDon);
                } else {
                    // Cập nhật user
                    newHoaDon.setId(hoadon.getId());
                    updateUser(newHoaDon, position);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        Log.d("Dialog", "Dialog đã tạo");
    }
    private void addUser(HoaDon user){
        Call<Response<HoaDon>> call = httpRequest.callApi().addHoaDon(user);
        call.enqueue(new Callback<Response<HoaDon>>() {
            @Override
            public void onResponse(Call<Response<HoaDon>> call, retrofit2.Response<Response<HoaDon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<HoaDon> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        fetchUserList();
                        Toast.makeText(MainActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi thêm user", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Response<HoaDon>> call, Throwable t) {

            }
        });
    }
    private void updateUser(HoaDon user, int position) {
        Call<Response<HoaDon>> call = httpRequest.callApi().updateHoaDon(user.getId(), user);
        call.enqueue(new Callback<Response<HoaDon>>() {
            @Override
            public void onResponse(Call<Response<HoaDon>> call, retrofit2.Response<Response<HoaDon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<HoaDon> apiResponse = response.body();
                    Log.d("apiEdit", "dữ liệu: "+apiResponse.toString());
                    if (apiResponse.getStatus() == 200) {
                        fetchUserList();
                        Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<HoaDon>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUser(String id, int position) {
        Call<Response<Void>> call = httpRequest.callApi().deleteHoaDon(id);
        call.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, retrofit2.Response<Response<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<Void> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        fetchUserList();
                        Toast.makeText(MainActivity.this, "xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "lỗi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}