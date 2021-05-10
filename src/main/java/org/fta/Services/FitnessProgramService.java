package org.fta.Services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fta.Models.FitnessProgramModel;
import org.fta.Models.ClientModel;
import org.fta.Exceptions.InvalidFieldException;
import org.fta.Exceptions.BlankFieldException;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Exceptions.InvalidCredentialsException;
import static org.fta.Services.FileSystemService.getPathToFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class FitnessProgramService {

    private static ObjectRepository<FitnessProgramModel> programRepository;
    private static int countProgram;
    private static int j=0;

    public static void initDatabase() {

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("programs-fta.db").toFile())
                .openOrCreate("test", "test");

        programRepository = database.getRepository(FitnessProgramModel.class);
    }

    public static void addProgram(String exerciseName, String reps, String sets, String zoomLink, String trainerName) {
        int rs=Integer.parseInt(reps);
        int ss=Integer.parseInt(sets);
        programRepository.insert(new FitnessProgramModel(exerciseName, rs, ss, zoomLink, trainerName));
    }

    public static void editProgram(String exerciseName, String reps, String sets, String zoomLink, String trainerName) {
        int rs=Integer.parseInt(reps);
        int ss=Integer.parseInt(sets);
        for (FitnessProgramModel program :  programRepository.find()) {
            if(Objects.equals(exerciseName, program.getExerciseName())) {
                program.setZoomLink(zoomLink);
                program.setSets(ss);
                program.setReps(rs);
                program.setTrainerName(trainerName);
            }
            programRepository.update(program);
        }
    }

    public static void deleteProgram(String exerciseName, String reps, String sets, String zoomLink, String trainerName) {
        for (FitnessProgramModel program : programRepository.find()) {
            if(Objects.equals(exerciseName, program.getExerciseName()))
                programRepository.remove(program);
        }
    }

    public static void checkBlankFieldsException(String exerciseName, String reps, String sets, String zoomLink, String trainerName) throws BlankFieldException {
        if(exerciseName.isBlank() || reps.isBlank() || sets.isBlank() || zoomLink.isBlank() || trainerName.isBlank())
            throw new BlankFieldException();
    }

    public static void verifyBlankFields(String exerciseName, String reps, String sets, String zoomLink, String trainerName) throws BlankFieldException {
        checkBlankFieldsException(exerciseName, reps, sets, zoomLink, trainerName);
    }

    public static void checkInvalidFieldsException(String exerciseName, String reps, String sets, String zoomLink, String trainerName) throws InvalidFieldException {
        int count=0;
        for(FitnessProgramModel program : programRepository.find()) {
            if(Objects.equals(exerciseName,program.getExerciseName()))
            {
                count++;
            }
        }
        if(count==0)
        {
            throw new InvalidFieldException();
        }
    }

    public static void verifyInvalidFields(String exerciseName, String reps, String sets, String zoomLink, String trainerName) throws InvalidFieldException{
        checkInvalidFieldsException(exerciseName, reps, sets, zoomLink, trainerName);
    }

    public static int getProgramNumber() {
        countProgram=0;
        for (FitnessProgramModel program : programRepository.find()) {
            countProgram++;
        }
        return countProgram;
    }

    public static FitnessProgramModel returnProgram(int i)  {
        j=0;
        for (FitnessProgramModel program : programRepository.find()) {
            j++;
            if (i==j)
                return program;

        }
        return null;
    }

}


























