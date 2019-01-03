public class MainActivity extends AppCompatActivity {

    List<Person> list = new ArrayList<>();
    PersonAdapter pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pa = new PersonAdapter(this, 0, list);
        list.add(new Person("john", "doe"));
        list.add(new Person("timmy", "mage"));

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(pa);
    }

    class Person {
        String name, lastname;

        public Person(String name, String lastname) {
            this.name = name;
            this.lastname = lastname;
        }
    }

    class PersonAdapter extends ArrayAdapter<Person> {
        public PersonAdapter(Context context, int resource, List<Person> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);

                Person p = getItem(position);
                TextView textView1 = convertView.findViewById(R.id.textView1);
                TextView textView2 = convertView.findViewById(R.id.textView2);
                textView1.setText(p.name);
                textView2.setText(p.lastname);
            }
            return convertView;
        }
    }
}
