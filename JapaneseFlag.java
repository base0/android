public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        Paint paint;

        public MyView(Context context) {
            super(context);

            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int x = canvas.getWidth() / 2;
            int y = canvas.getHeight() / 2;
            int r = Math.min(canvas.getWidth(), canvas.getHeight()) / 4;
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, r, paint);
        }
    }
}
