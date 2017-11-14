使用方法
final EditText editText = findViewById(R.id.edt);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardView keyboardView = new KeyboardView(MainActivity.this);
                keyboardView.setEditText(editText);
                keyboardView.show();
            }
        });
