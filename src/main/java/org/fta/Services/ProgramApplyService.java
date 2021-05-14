package org.fta.Services;
import java.util.Objects;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import org.fta.Models.FitnessProgramModel;
import org.fta.Models.ProgramApplyModel;
import static org.fta.Services.FileSystemService.getPathToFile;

public class ProgramApplyService {
    private static int count = 0;
    private static ObjectRepository<ProgramApplyModel> programApplyRepository;

    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("applicants.db").toFile())
                .openOrCreate("test", "test");
        programApplyRepository = database.getRepository(ProgramApplyModel.class);
    }

    public static void addApplicationToDatabase(String customerName, String customerTrainingLevel, String exerciseName){
        programApplyRepository.insert(new ProgramApplyModel(customerName, customerTrainingLevel, exerciseName));
    }

    private static int k=0;
    public static ProgramApplyModel returnProgramApply(int i){
        k = 0;
        for(ProgramApplyModel programApply : programApplyRepository.find()){
            k++;
            if(k==i){
                return programApply;
            }
        }
        return null;
    }

    public static int getProgramApplyNumber(){
        count = 0;
        for(ProgramApplyModel programApply : programApplyRepository.find()){
            count++;
        }
        return count;
    }

    public static void acceptApplication(String customerName, String exerciseName){
        for(ProgramApplyModel programApply : programApplyRepository.find()){
            if(Objects.equals(customerName, programApply.getCustomerName()) && Objects.equals(exerciseName, programApply.getExerciseName()))
                programApplyRepository.remove(programApply);
        }
    }

    public static void denyApplication(String customerName, String exerciseName){
        for(ProgramApplyModel programApply : programApplyRepository.find()){
            if(Objects.equals(customerName, programApply.getCustomerName()) && Objects.equals(exerciseName, programApply.getExerciseName()))
                programApplyRepository.remove(programApply);
        }
    }

}
