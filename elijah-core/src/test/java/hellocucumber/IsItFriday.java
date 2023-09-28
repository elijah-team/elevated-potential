package hellocucumber;

class IsItFriday {
    static String isItFriday(String today) {
        if ("Friday".equals(today))
            return "TGIF";
        return "Nope";
    }
}
