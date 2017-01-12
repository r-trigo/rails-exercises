/**
 * Created by e348900 on 12-01-2017.
 */
function assisted_search(dropdown_text) {
    switch (dropdown_text) {
        case 'All':
            textfield_text = '';
            break;
        case 'Kanto':
            textfield_text = 'kt'
            break;
        case 'Johto':
            textfield_text = 'jo'
            break;
        case 'Hoenn':
            textfield_text = 'ho';
            break;
        case 'Sinnoh':
            textfield_text = 'si'
            break;
        case 'Unova':
            textfield_text = 'un'
            break;
        case 'Kalos':
            textfield_text = 'kl'
            break;
        case 'Alola':
            textfield_text = 'al'
            break;
    }
    return textfield_text;
}