package ru.hw12.listeners;

import ru.hw12.base.DBService;
import ru.hw12.base.dataSet.AddressDataSet;
import ru.hw12.base.dataSet.PhoneDataSet;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.dbService.DBServiceImpl;
import ru.hw12.utils.EhCacheUtil;

import javax.cache.Cache;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebListener
public class AppInitializerListener implements ServletContextListener
{
    private DBService dbService;

    @Override
    public void contextInitialized(final ServletContextEvent sce)
    {
        try {
            Cache<Long, UserDataSet> cache = new EhCacheUtil().getDataSetCache("userDataSetCache", Long.class, UserDataSet.class);

            dbService = new DBServiceImpl(cache);

            String status = dbService.getLocalStatus();
            System.out.println("Status: " + status);

           List<AddressDataSet> addresses = getAddressList();

            for (int i = 0; i < 5; i++) {
                dbService.save(getUser("user" + i +1, addresses.get(i)));
            }

            UserDataSet user5 = dbService.read(5);
            user5.setName("Andrew22");

            dbService.save(user5);
            UserDataSet aUser = dbService.readByName("Andrew22");

            user5 = dbService.read(5);
            UserDataSet user3 = dbService.read(3);
            UserDataSet user4 = dbService.read(4);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce)
    {
        dbService.shutdown();
    }

    public static UserDataSet getUser(final String name, final AddressDataSet address)
    {
        UserDataSet user = new UserDataSet();
        user.setName(name);
        user.setAge(new Random().nextInt(50) + 15);
        user.addAddress(address);
        user.addPhoneNumber(new PhoneDataSet("00000 555888"));
        user.addPhoneNumber(new PhoneDataSet("22222 444777"));
        return user;
    }

    public static List<AddressDataSet> getAddressList()
    {
        List<AddressDataSet> addresses = new ArrayList<>();
        addresses.add(new AddressDataSet("Brick Lane"));
        addresses.add(new AddressDataSet("Old Str"));
        addresses.add(new AddressDataSet("Liverpool Str"));
        addresses.add(new AddressDataSet("Chelsea"));
        addresses.add(new AddressDataSet("Bristol"));
        return addresses;
    }
}
