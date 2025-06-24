package com.softwaretesting.testing.validator;

import org.springframework.stereotype.Service;

// Validates international phone numbers according to the E.164 standard.
// Rules:
// - Must start with a plus sign (+)
// - First digit after the plus must be between 1-9 (no leading zero)
// - Only digits allowed after the plus
// - Maximum total length of 15 digits
//
// Sources used:
// - https://www.bundesnetzagentur.de/DE/Sachgebiete/Telekommunikation/Unternehmen_Institutionen/Nummerierung/start.html
// - https://en.wikipedia.org/wiki/E.164
//
// Additional Notes:
// - This validator checks only the international format (+49... for Germany).
// - Specific national numbering structures (e.g., local area codes, service numbers)
//   are NOT considered in this validation.
// - For more information about German national numbering structures,
//   refer to the Bundesnetzagentur numbering plan (Nummernraum PDF):
//   https://www.bundesnetzagentur.de/DE/Fachthemen/Telekommunikation/Nummerierung/_DL/np_nummernraum.pdf?__blob=publicationFile&v=1
@Service
public class PhoneNumberValidator {

    public boolean validate(String phoneNumber){
        if (phoneNumber == null)
            throw new NullPointerException("Phone number must not be null");

        return phoneNumber.matches("\\+[1-9][0-9]{1,13}$");
    }
}
