package com.example.laboratoirejeuastre;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.PointF;
import java.util.Random;
import java.util.List;
import android.graphics.RectF;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;



public class SolarSystemView extends View implements SensorEventListener {

    private Bitmap spaceshipBitmap;
    private float spaceshipX, spaceshipY;
    private String controlMode;
    private DatabaseAdapter databaseAdapter;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private List<Planet> planets;

    public SolarSystemView(Context context, String controlMode) {
        super(context);
        this.controlMode = controlMode;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        init();
    }

    private void init() {
        spaceshipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vaisseau_spatial);
        spaceshipX = getWidth() / 2;
        spaceshipY = getHeight() / 2;
        databaseAdapter = new DatabaseAdapter(getContext());
        planets = databaseAdapter.getAllPlanets();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        spaceshipX = w / 2 - spaceshipBitmap.getWidth() / 2;
        spaceshipY = h / 2 - spaceshipBitmap.getHeight() / 2;
        if (planets.isEmpty()) {
            initializePlanets();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(spaceshipBitmap, spaceshipX, spaceshipY, null);
        drawPlanets(canvas);
        if (isGameFinished()) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(100);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Game Finished", getWidth() / 2, getHeight() / 2, paint);
        }
    }

    private void drawPlanets(Canvas canvas) {
        for (Planet planet : planets) {
            String resourceName = planet.getImage();
            if ("touched".equals(planet.getStatus())) {
                resourceName = "planet_green"; // Le nom de votre ressource pour la planète verte
            }
            Bitmap planetBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(planet.getImage(), "drawable", getContext().getPackageName()));
            planetBitmap = Bitmap.createScaledBitmap(planetBitmap,Planet.STANDARD_SIZE, Planet.STANDARD_SIZE,true);
            canvas.drawBitmap(planetBitmap, planet.getX(), planet.getY(), null);
        }
    }
    public PointF getNonOverlappingPosition(int planetSize) {
        Random random = new Random();
        PointF position;
        boolean overlaps;

        do {
            position = new PointF(random.nextFloat() * (getWidth() - planetSize), random.nextFloat() * (getHeight() - planetSize));
            overlaps = false;

            for (Planet planet : planets) {
                if (RectF.intersects(new RectF(position.x, position.y, position.x + planetSize, position.y + planetSize),
                        new RectF(planet.getX(), planet.getY(), planet.getX() + planetSize, planet.getY() + planetSize))) {
                    overlaps = true;
                    break;
                }
            }
        } while (overlaps);

        return position;
    }
    public void initializePlanets() {

        if (planets.isEmpty()) {
            addPlanet("Mercure", "p1");
            addPlanet("Venus", "p2");
            addPlanet("saturne", "p3");
            addPlanet("jupiter", "p4");
            // Ajoutez d'autres planètes de la même manière
        }
    }

    private void addPlanet(String name, String imageResourceName) {
        PointF position = getNonOverlappingPosition(Planet.STANDARD_SIZE);
        Planet planet = new Planet(name, Planet.STANDARD_SIZE, "inactive", imageResourceName, position.x, position.y);
        databaseAdapter.addPlanet(planet);
        planets.add(planet);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ("touch".equals(controlMode)) {
            spaceshipX = event.getX() - spaceshipBitmap.getWidth() / 2;
            spaceshipY = event.getY() - spaceshipBitmap.getHeight() / 2;
            checkCollisions();
            invalidate();
        }
        return true;
    }


    public void registerAccelerometerListener() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterAccelerometerListener() {
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            spaceshipX -= sensorEvent.values[0];
            spaceshipY += sensorEvent.values[1];
            checkCollisions();
            invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void checkCollisions() {
        for (Planet planet : planets) {
            if (!"touched".equals(planet.getStatus())) {
                Bitmap planetBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(planet.getImage(), "drawable", getContext().getPackageName()));
                planetBitmap = Bitmap.createScaledBitmap(planetBitmap, Planet.STANDARD_SIZE, Planet.STANDARD_SIZE, true);

                float planetCenterX = planet.getX() + planetBitmap.getWidth() / 2;
                float planetCenterY = planet.getY() + planetBitmap.getHeight() / 2;
                float spaceshipCenterX = spaceshipX + spaceshipBitmap.getWidth() / 2;
                float spaceshipCenterY = spaceshipY + spaceshipBitmap.getHeight() / 2;

                float distance = (float) Math.sqrt(Math.pow(planetCenterX - spaceshipCenterX, 2) + Math.pow(planetCenterY - spaceshipCenterY, 2));
                if (distance < (planetBitmap.getWidth() + spaceshipBitmap.getWidth()) / 2) {
                    planet.setStatus("touched");
                    databaseAdapter.updatePlanet(planet);
                }
            }
        }
    }
    private boolean isGameFinished() {
        for (Planet planet : planets) {
            if (!planet.getStatus().equals("touched")) {
                return false;
            }
        }
        return true;
    }


}
