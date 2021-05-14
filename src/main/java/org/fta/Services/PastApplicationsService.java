package org.fta.Services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fta.Models.PastApplicationsModel;

import static org.fta.Services.FileSystemService.getPathToFile;

public class PastApplicationsService {

    private static int count = 0;
    private static ObjectRepository<PastApplicationsModel> pastApplicationsRepository;

    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("pastApplications.db").toFile())
                .openOrCreate("test", "test");
        pastApplicationsRepository = database.getRepository(PastApplicationsModel.class);
    }

    public static void addPastApplicationToDatabase(String exerciseName, String customerName, String zoomLink){
        pastApplicationsRepository.insert(new PastApplicationsModel(exerciseName, customerName, zoomLink));
    }

    private static int k=0;
    public static PastApplicationsModel returnPastApplication(int i){
        k = 0;
        for(PastApplicationsModel pastApplications : pastApplicationsRepository.find()){
            k++;
            if(k==i){
                return pastApplications;
            }
        }
        return null;
    }

    public static int getPastApplicationNumber(){
        count = 0;
        for(PastApplicationsModel pastApplications : pastApplicationsRepository.find()){
            count++;
        }
        return count;
    }
}
