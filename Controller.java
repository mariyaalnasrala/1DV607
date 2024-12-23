package controller;

import java.util.List;
import java.util.Optional;
import model.Contract;
import model.ContractFactory;
import model.DataInitializer;
import model.Item;
import model.ItemCategory;
import model.ItemFactory;
import model.Member;
import model.MemberFactory;
import model.Time;
import view.MemberInfoView;
import view.UserInterface;

/**
 * Controller class that coordinates actions within the application.
 */
public class Controller {

  private UserInterface ui;
  private Member memberRepo;
  private Item itemRepo;
  private Contract contractRepo;
  private Time timeRepo;
  private MemberFactory memberFac;
  private ItemFactory itemFac;
  private ContractFactory contractFac;
  private DataInitializer dataInitializer;

  /**
   * Constructs a Controller with the specified UI interface.
   *
   * @param ui The UI interface used for user interaction.
   */
  public Controller(UserInterface ui) {
    this.ui = ui.copy();
    this.memberRepo = new Member();
    this.contractRepo = new Contract();
    this.itemRepo = new Item();
    this.contractFac = new ContractFactory();
    this.memberFac = new MemberFactory();
    this.itemFac = new ItemFactory();
    this.timeRepo = new Time();
    new Member();
    new MemberInfoView();
    this.dataInitializer = new DataInitializer();
  }

  public void initializeData() {
    dataInitializer.initialize();
  }

  /**
   * Starts the main application loop, displaying the menu and handling user
   * selections.
   */
  public void run() {
    boolean running = true;
    while (running) {
      int optionInput = ui.getMenuOption();
      MenuOption option = MenuOption.fromInt(optionInput);

      if (option != null) {
        switch (option) {
          case ADD_MEMBER -> addMember();
          case LIST_MEMBERS_SIMPLE -> listMembersSimple();
          case UPDATE_MEMBER -> updateMember();
          case DELETE_MEMBER -> deleteMember();
          case ADD_ITEM -> addItem();
          case LIST_ITEMS -> listItems();
          case UPDATE_ITEM -> updateItem();
          case DELETE_ITEM -> deleteItem();
          case CREATE_CONTRACT -> createContract();
          case LIST_CONTRACTS -> listContracts();
          case ADVANCE_DAY -> advanceDay();
          case EXIT -> {
            exitApplication();
            running = false;
          }
          case LIST_MEMBERS_VERBOSE -> listMembersVerbose();
          default -> ui.displayErrorMessage();
        }
      } else {
        ui.displayErrorMessage();
      }
    }
  }

  private void addMember() {
    while (true) {

      try {
        Object[] memberData = ui.getMemberInfoView().collectMemberCreationInput();

        String name = (String) memberData[0];
        String email = (String) memberData[1];
        String phone = (String) memberData[2];

        Member member = memberFac.createMember(name, email, phone);
        memberRepo.addMember(member);
        ui.getMemberInfoView().displayMemberCreationSuccess(member);

        break;
      } catch (Exception e) {
        ui.displayErrorMessage();
      }
    }
  }

  private void updateMember() {
    while (true) {
      String memberId = ui.getMemberId();
      Optional<Member> memberOpt = memberRepo.findMemberById(memberId);

      if (!memberOpt.isPresent()) {
        ui.displayErrorMessage();
        continue;
      }

      Member member = memberOpt.get();
      Object[] updatedData = ui.getMemberInfoView().collectMemberUpdateInput();

      String newName = (String) updatedData[0];
      String newEmail = (String) updatedData[1];
      String newPhone = (String) updatedData[2];

      String validationMessage = ui.getMemberInfoView().validateMemberInputs(newName,
          newEmail, newPhone);
      if (validationMessage != null) {
        ui.displayErrorMessage();
        continue;
      }

      try {
        member.updateMember(newName, newEmail, newPhone);
        ui.getMemberInfoView().displayMemberUpdateSuccess(member);

        break;
      } catch (IllegalArgumentException e) {
        ui.displayErrorMessage();
      }

    }
  }

  private void deleteMember() {
    while (true) {
      String memberId = ui.deleteMemberById();
      Optional<Member> memberOpt = memberRepo.findMemberById(memberId);

      if (memberOpt.isEmpty()) {
        ui.memberNotFound();
        continue;
      }

      memberOpt.ifPresentOrElse(member -> {
        if (itemRepo.getAllItemsCopy().stream().noneMatch(item -> item.getOwner().equals(member))) {
          memberRepo.removeMember(member);
          ui.deleteMemberSuccessMessage();
        } else {
          ui.deleteMemberErrorMessage();
        }
      }, () -> ui.memberNotFound());
      break;
    }
  }

  private void addItem() {
    while (true) {
      try {

        List<Member> members = memberRepo.getAllMembersCopy();

        Object[] itemData = ui.getItemInfoView().collectItemCreationInput(members);

        String name = (String) itemData[0];
        String description = (String) itemData[1];
        ItemCategory category = (ItemCategory) itemData[2];
        double costPerDay = (double) itemData[3];
        Member owner = (Member) itemData[4];

        Optional<Member> ownerOpt = memberRepo.findMemberById(owner.getId());
        if (ownerOpt.isEmpty()) {
          ui.displayErrorMessage();
          continue;
        }

        Item item = itemFac.createItem(name, description, category, costPerDay, owner);
        itemRepo.addItem(item);

        ownerOpt.get().addItem(item);
        ui.getItemInfoView().displayItemCreationSuccess(item);

        break;
      } catch (IllegalArgumentException e) {
        ui.displayErrorMessage();
      } catch (Exception e) {
        ui.displayErrorMessage();
      }

    }
  }

  private void updateItem() {
    while (true) {
      int itemId = ui.updateItemById();
      Optional<Item> itemOpt = itemRepo.findItemById(itemId);

      itemOpt.ifPresentOrElse(item -> {
        Object[] updatedData = ui.getItemInfoView().collectItemUpdateInput();

        try {
          String newName = (String) updatedData[0];
          String newDescription = (String) updatedData[1];
          ItemCategory newCategory = (ItemCategory) updatedData[2];

          if (newCategory == null) {
            ui.invaildCategory();
            return;
          }

          Double newCostPerDay = (Double) updatedData[3];
          if (newCostPerDay <= 0) {
            ui.negativeCost();
            return;
          }

          item.updateItem(newName, newDescription, newCategory, newCostPerDay);
          ui.getItemInfoView().displayItemUpdateSuccess(item);

        } catch (ClassCastException e) {
          ui.displayErrorMessage();
        }
      }, () -> ui.itemNotFound());

      break;
    }
  }

  private void deleteItem() {
    while (true) {
      int itemId = ui.deleteItemById();
      Optional<Item> itemOpt = itemRepo.findItemById(itemId);

      if (itemOpt.isPresent()) {
        Item item = itemOpt.get();
        if (contractRepo.isItemInvolvedInFutureOrActiveContract(item)) {
          ui.deleteItemFaild();
          break;
        } else {
          if (item.getOwner() != null) {
            item.getOwner().removeItem(item);
          }
          itemRepo.removeItem(item);
          ui.deleteItemSuccess();
          break;
        }
      } else {
        ui.itemNotFound();

      }

    }
  }

  private void createContract() {
    while (true) {
      try {
        Object[] contractData = ui.getContractInfoView().collectContractCreationInput();

        int itemId = (int) contractData[0];
        String renterId = (String) contractData[1];
        int startDay = (int) contractData[2];
        int endDay = (int) contractData[3];

        if (endDay <= startDay) {
          ui.displayErrorMessage();
          continue;
        }

        Optional<Item> itemOpt = itemRepo.findItemById(itemId);
        Optional<Member> renterOpt = memberRepo.findMemberById(renterId);

        if (itemOpt.isPresent() && renterOpt.isPresent()) {

          Item item = itemOpt.get();

          if (contractRepo.hasDateConflict(item, startDay, endDay)) {
            ui.displayErrorMessage();
            continue;
          }

          Contract contract = contractFac.createContract(itemOpt.get(), renterOpt.get(),
              startDay, endDay);
          contractRepo.addContract(contract);
          ui.getContractInfoView().displayContractCreationSuccess(contract);

          break;
        } else {
          ui.invalidRenterId();
        }
      } catch (Exception e) {
        ui.displayErrorMessage();
      }
    }
  }

  private void advanceDay() {
    while (true) {
      int daysToAdvance = ui.numOfDays();
      if (daysToAdvance > 0) {
        timeRepo.advanceDays(daysToAdvance);
        ui.displayDayAdvanced(timeRepo.getCurrentDay());
        contractRepo.processDueContracts(timeRepo.getCurrentDay());

        break;
      } else {
        ui.negativeNumOfDays();
      }
    }
  }

  private void exitApplication() {
    ui.displayExitMessage();
  }

  private void listMembersSimple() {
    ui.getMemberInfoView().displayMembersSimple(dataInitializer.getAllMembersCopy(), memberRepo.getAllMembersCopy());
    // List<Member> members = memberRepo.getAllMembersCopy();
    // ui.getMemberInfoView().displayMembersSimple(members);
  }

  private void listMembersVerbose() {
    ui.getMemberInfoView().displayMembersVerbose(dataInitializer.getAllMembersCopy(), memberRepo.getAllMembersCopy());
    // List<Member> members = memberRepo.getAllMembersCopy();
    // ui.getMemberInfoView().displayMembersVerbose(members);
  }

  private void listItems() {
    List<Item> items = itemRepo.getAllItemsCopy();

    ui.getItemInfoView().displayItems(items);
  }

  private void listContracts() {
    List<Contract> contracts = contractRepo.getAllContractsCopy();
    if (contracts.isEmpty()) {
      ui.contractNotFound();
    } else {
      ui.getContractInfoView().displayContracts(contracts);
    }
  }

}
