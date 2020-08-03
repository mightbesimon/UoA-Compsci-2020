from AssCool_game import *


''' COMPSCI 130 (2020) - University of Auckland
    ASSIGNMENT ONE - Connect Four
    Simon Shan  441147157
'''

def get_input():
    valid_opts = [str(opt+1) for opt in range(5)]
    prompt = f' - please select an option: '
    choice = input(prompt)

    # check valid input
    while True:
        if choice not in valid_opts:
            print(' - ** INVALID INPUT **')
        else:
            break
        # re-prompt input
        choice = input(prompt)

    print('\n'*10)
    return choice

def main():
    print('''
################################
##=====   CONNECT FOUR!  =====##
################################

    OPTIONS:
        1: 2-players
        2: 1 player, 1 AI
        3: 3-players
        4: 4-players
        5: quit
''')

    choice = get_input()
    if choice=='1':
        game = Game(num_players=2)
    elif choice=='2':
        game = Game(num_players=2)
        game.players[1].isAI = True
    elif choice=='3':
        game = Game(num_players=3)
    elif choice=='4':
        game = Game(num_players=4)
    else:
        return

    while not game.over:
        game.loop()

    game.clean_up()


if __name__ == '__main__': main()

