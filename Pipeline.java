package org.firstinspires.ftc.teamcode.DemoRobot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipeline extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();

    public double totalA = 0;
    public double totalB = 0;
    public double totalC = 0;


    public int position = 1;
    public static int matAcolumnStart = 200;
    public static int matAcolumnEnd = 479;
    public static int matArowStart = 300;
    public static int matArowEnd = 640;


    public Pipeline() {

    }

    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }


        Mat matA = workingMatrix.submat(matAcolumnStart, matAcolumnEnd, matArowStart,
                matArowEnd);

        Imgproc.rectangle(workingMatrix, new Rect(matAcolumnStart, matArowStart,
                (matArowEnd - matArowStart), (matAcolumnEnd - matAcolumnStart)), new Scalar(0, 255, 0));

        //Average pixel color for each color
        totalA = Core.sumElems(matA).val[0];
        totalA /= matA.rows() * matA.cols();
        totalB = Core.sumElems(matA).val[1];
        totalB /= matA.rows() * matA.cols();
        totalC = Core.sumElems(matA).val[2];
        totalC /= matA.rows() * matA.cols();



        if((totalA > totalB) && (totalA > totalC)){
            position = 1;
        } else if((totalC > totalB) && (totalC > totalA)){
            position = 2;
        } else{
            position = 3;
        }
        return workingMatrix;
    }
}
