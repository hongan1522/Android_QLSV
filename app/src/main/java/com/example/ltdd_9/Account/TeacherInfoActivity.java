package com.example.ltdd_9.Account;

import androidx.appcompat.app.AppCompatActivity;


public class TeacherInfoActivity extends AppCompatActivity {

//    // Khai báo các thành phần của giao diện
//    EditText editTextName;
//    EditText editTextGender;
//    EditText editTextEmail;
//    EditText editTextAddress;
//    EditText editTextPassword;
//    EditText editTextRole;
//    Button buttonSave;
//    EditText editUsername, editPassword;
//    // Khai báo đối tượng DAO
//    TeacherDAO teacherDAO;
//    private DataBase db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teacher_info);
//
//        // Ánh xạ các thành phần từ giao diện
//        editTextName = findViewById(R.id.editTextName);
//        editTextGender = findViewById(R.id.editTextGender);
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextAddress = findViewById(R.id.editTextAddress);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        editTextRole = findViewById(R.id.editTextRole);
//        buttonSave = findViewById(R.id.buttonSave);
//        editUsername = findViewById(R.id.editUsername);
//        editPassword = findViewById(R.id.editPass);
//        // Khởi tạo đối tượng DAO
//        teacherDAO = new TeacherDAO(this);
//        db = new DataBase(this);
//        // Đặt sự kiện click cho buttonSave
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Lấy dữ liệu từ các EditText
//                String name = editTextName.getText().toString();
//                String gender = editTextGender.getText().toString();
//                String email = editTextEmail.getText().toString();
//                String address = editTextAddress.getText().toString();
//                String password = editTextPassword.getText().toString();
//                int role = Integer.parseInt(editTextRole.getText().toString());
//
//
//
//
//                // Tạo đối tượng Teacher từ dữ liệu
//                Teacher teacher = new Teacher();
//                teacher.setName(name);
//                teacher.setGender(gender);
//                teacher.setEmail(email);
//                teacher.setAddress(address);
//                teacher.setPassword(password);
//                teacher.setRole(role);
//
//                boolean isSuccess = teacherDAO.insertTeacherData(teacher);
//
//
//                if (isSuccess) {
//                    int teacherId = teacher.getTeacherId();
//
//
//                    String username = editUsername.getText().toString();
//                    String pass = editPassword.getText().toString();
//
//                    Account account = new Account(name, "gmail", username, pass, 0, teacherId);
//
//                    // Thêm người dùng vào bảng User
//                    db.addUserIsTeacher(account);
//
//                    // Log thông tin về teacherId
//                    Log.i("user cua teacher", String.valueOf(teacherId));
//
//                    Toast.makeText(TeacherInfoActivity.this, "Thêm mới giáo viên thành công", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(TeacherInfoActivity.this, "Thêm mới giáo viên thất bại", Toast.LENGTH_SHORT).show();
//                    // Xử lý sau khi thêm thất bại (nếu cần)
//                }
//            }
//        });
//    }
}
