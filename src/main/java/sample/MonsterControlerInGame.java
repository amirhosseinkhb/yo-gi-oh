package sample;

import sample.model.Card.Field;
import sample.model.Card.MonsterForUser;
import sample.model.Card.Position;
import sample.model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonsterControlerInGame {

    private static Scanner scanner = new Scanner(System.in);

    public static void selectedMonsterFromZone(MonsterForUser monsterForUser, User user, User opponent, String phase) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("select -d")) {
            System.out.println("tof to in zendegi");
            input = scanner.nextLine();
            boolean checker = false;

            Pattern pattern = Pattern.compile("card show --selected");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                ProgramController.CardShow(monsterForUser.getName());
            }

             pattern = Pattern.compile("set --position attack");
             matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("phase1") || phase.equals("phase2")) {
                    positionAttack(monsterForUser);
                } else {
                    System.out.println("you can’t do this action in this phase");
                }
            }
//in ke har dast ye bar avaz mishe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            pattern = Pattern.compile("set --position defense");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("phase1") || phase.equals("phase2")) {
                    positionDefend(monsterForUser);
                } else {
                    System.out.println("you can’t do this action in this phase");
                }
            }

            pattern = Pattern.compile("flip-summon");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("phase1") || phase.equals("phase2")) {
                    flipSummon(monsterForUser);
                } else {
                    System.out.println("you can’t do this action in this phase");
                }
            }

            pattern = Pattern.compile("attack direct");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("battle")) {
                    System.out.println("khobi?");
                    boolean checkIfOpponentMonsterZoneEmpty = true;
                    for (int a=0;a<5;a++) {
                        if (opponent.monsterZone[a] != null) {
                            checkIfOpponentMonsterZoneEmpty = false;
                            break;
                        }
                    }
                    if (checkIfOpponentMonsterZoneEmpty) {
                        opponent.lifePoint -= monsterForUser.ATK;
                        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        System.out.println("you opponent receives " + monsterForUser.ATK + " battale damage");
                    } else {
                        System.out.println("you can’t attack the opponent directly");
                    }
                } else {
                    System.out.println("you can’t do this action in this phase");
                }
            }

            pattern = Pattern.compile("attack ([\\d]+)");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                int address = Integer.parseInt(matcher.group(1));
                address--;
                if (phase.equals("battle")) {
                    boolean checkIfOpponentMonsterZoneEmpty = true;
                    if (opponent.monsterZone[address] != null) {
                        checkIfOpponentMonsterZoneEmpty = false;
                    }
                    if (!checkIfOpponentMonsterZoneEmpty) {
                        Game.attack(monsterForUser, opponent.monsterZone[address], user, opponent);
                    } else {
                        System.out.println("there is no card to attack here");
                    }
                } else {
                    System.out.println("you can’t do this action in this phase");
                }
            }


        }
    }


    public static void monsterSelectedFromHand(MonsterForUser monsterForUser, User user, String phase) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("select -d")) {
            System.out.println("hi1");
            input = scanner.nextLine();
            boolean checker = false;

            Pattern pattern = Pattern.compile("card show --selected");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                ProgramController.CardShow(monsterForUser.getName());
            }

            pattern = Pattern.compile("summon");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("phase1") || phase.equals("phase2")) {
                    //!!!!!!!!!!!!!!!!!!!!!!has summon???
                    if (Game.hasSummonInThisRound) {
                        System.out.println("you already summoned/set on this turn");
                    } else {

                        summonControler(monsterForUser, user);

                    }
                } else {
                    System.out.println("action not allowed in this phase");
                }
            }

            pattern = Pattern.compile("set");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                checker = true;
                if (phase.equals("phase1") || phase.equals("phase2")) {
                    if (Game.hasSummonInThisRound) {
                        System.out.println("you already summoned/set on this turn");
                    } else {
                        setController(monsterForUser, user);
                    }
                } else System.out.println("action not allowed in this phase");
            }


            if (!checker) {
                System.out.println("invalid input");
            }
        }
    }


    private static void setController(MonsterForUser monsterForUser, User user) {
        if (monsterForUser.level <= 4) {
            set(monsterForUser, user);
        } else {
            if (tribute(monsterForUser, user)) {
                set(monsterForUser, user);
            }
        }
    }

    private static void set(MonsterForUser monsterForUser, User user) {
        boolean hasEmpty = false;
        for (int a=0;a<5;a++) {
            if (user.monsterZone[a] == null) {
                System.out.println("hichkaas"+a);
                hasEmpty = true;
                monsterForUser.field = Field.valueOf("GAME");
                monsterForUser.address = a;
                monsterForUser.position = Position.valueOf("HIDDEN");
                user.monsterZone[a] = monsterForUser;
                user.handMonster.remove(monsterForUser);
                Game.hasSummonInThisRound=true;
                System.out.println("set successfully");
                break;
            }
        }
        if (!hasEmpty) {
            System.out.println("monster card zone is full");
        }
    }

    public static void summonControler(MonsterForUser monsterForUser, User user) {
        if (monsterForUser.level <= 4) {
            summon(monsterForUser, user);

        } else {
            if (tribute(monsterForUser, user)) {
                summon(monsterForUser, user);
            }
        }
    }

    private static void summon(MonsterForUser monsterForUser, User user) {
        boolean hasEmpty = false;
        for (int a=0;a<5;a++) {
            if (user.monsterZone[a] == null) {
                System.out.println("yas"+a);
                hasEmpty = true;
                monsterForUser.field = Field.valueOf("GAME");
                monsterForUser.address = a;
                monsterForUser.position = Position.valueOf("ATTACK");
                user.monsterZone[a] = monsterForUser;
                user.handMonster.remove(monsterForUser);
                Game.hasSummonInThisRound=true;
                System.out.println("summoned successfully");
                break;
            }
        }
        if (!hasEmpty) {
            System.out.println("monster card zone is full");
        }
    }

    private static boolean tribute(MonsterForUser monsterForUser, User user) {
        if (monsterForUser.level == 5 || monsterForUser.level == 6) {
            boolean hasAnyCard = false;
            for (int a=0;a<5;a++) {
                if (user.handMonster != null) {
                    hasAnyCard = true;

                }
            }

            if (hasAnyCard) {
                System.out.println("enter an address to tribute");
                String temp = scanner.nextLine();
                int address = Integer.parseInt(temp);
                address--;
                if (user.monsterZone[address] == null) {
                    System.out.println("there no monsters one this address");
                } else {
                    user.monsterZone[address].field = Field.valueOf("GRAVE");
                    user.monsterGrave.add(user.monsterZone[address]);
                    user.monsterZone[address].address = user.NumOfGrave;
                    user.NumOfGrave++;
                    user.monsterZone[address] = null;
                    System.out.println("done");
                    return true;
                }
            } else {
                System.out.println("there are not enough cards for tribute");
                return false;
            }
        }


        if (monsterForUser.level > 6) {
            int checker = 0;
            for (int a=0;a<5;a++) {
                if (user.handMonster != null) {
                    checker++;
                }
            }

            if (checker >= 2) {
                System.out.println("enter an address to tribute");
                String temp = scanner.nextLine();
                int address = Integer.parseInt(temp);
                address--;
                if (user.monsterZone[address] == null) {
                    System.out.println("there no monsters one this address");
                } else {
                    System.out.println("enter an address to tribute");
                    temp = scanner.nextLine();
                    int address1 = Integer.parseInt(temp);
                    address1--;
                    if (user.monsterZone[address1] == null) {
                        System.out.println("there no monsters one this address");
                    } else {
                        user.monsterZone[address].field = Field.valueOf("GRAVE");
                        user.monsterGrave.add(user.monsterZone[address]);
                        user.monsterZone[address].address = user.NumOfGrave;
                        user.NumOfGrave++;
                        user.monsterZone[address] = null;

                        user.monsterZone[address1].field = Field.valueOf("GRAVE");
                        user.monsterGrave.add(user.monsterZone[address1]);
                        user.monsterZone[address1].address = user.NumOfGrave;
                        user.NumOfGrave++;
                        user.monsterZone[address1] = null;

                        return true;
                    }
                }
            } else {
                System.out.println("there are not enough cards for tribute");
                return false;
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }


    private static void positionAttack(MonsterForUser monsterForUser) {
        if (monsterForUser.position.equals(Position.valueOf("ATTACK"))) {
            System.out.println("this card is already in the wanted position");
        } else {
            if (monsterForUser.position.equals(Position.valueOf("DEFEND"))) {
                monsterForUser.position = Position.valueOf("ATTACK");
                System.out.println("monster card position changed successfully");
            }
        }
    }

    private static void positionDefend(MonsterForUser monsterForUser) {
        if (monsterForUser.position.equals(Position.valueOf("DEFEND"))) {
            System.out.println("this card is already in the wanted position");
        } else {
            if (monsterForUser.position.equals(Position.valueOf("ATTACK"))) {
                monsterForUser.position = Position.valueOf("DEFEND");
                System.out.println("monster card position changed successfully");
            }
        }
    }

    private static void flipSummon(MonsterForUser monsterForUser) {
        if (monsterForUser.position.equals(Position.valueOf("HIDDEN"))) {
            monsterForUser.position = Position.valueOf("ATTACK");
        } else {
            System.out.println("you can’t flip summon this card");
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!age taze gozashte bashe nmishe flip krd!!!!!!!!!!!!!!!
        }
    }

}
