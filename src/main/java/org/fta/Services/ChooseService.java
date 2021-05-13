package org.fta.Services;

import static org.fta.Services.FileSystemService.getPathToFile;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import org.fta.Models.ChooseModel;
import org.fta.Models.FitnessProgramModel;

public class ChooseService {

    private static ObjectRepository<ChooseModel> ChooseProgramRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("chosen-programs.db").toFile())
                .openOrCreate("test", "test");

        ChooseProgramRepository = database.getRepository(ChooseModel.class);
    }

    public static void addChosenProgram(String trainer,String exercise)
    {
        ChooseProgramRepository.insert(new ChooseModel(trainer,exercise));
    }

    public static void removeChosen()
    {
        ChooseProgramRepository.remove(ObjectFilters.ALL);
    }
}
