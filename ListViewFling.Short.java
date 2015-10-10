final GestureDetector gd = new GestureDetector(this, new GestureDetector.OnGestureListener() {
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        int id = listView.pointToPosition((int) e.getX(), (int) e.getY());
        Log.i("gg", "long press " + id);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int id = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
        Log.i("gg", "fling " + id);
        return false;
    }
});

listView = (ListView) findViewById(R.id.listView);
listView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
});
listView.setAdapter(adapter);
